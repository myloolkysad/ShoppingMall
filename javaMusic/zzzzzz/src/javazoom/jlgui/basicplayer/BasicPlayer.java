/*
 * BasicPlayer.
 *
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
package javazoom.jlgui.basicplayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.spi.PropertiesContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * BasicPlayer is a threaded simple player class based on JavaSound API.
 * BasicPlayer�뒗 JavaSound API瑜� 湲곕컲�쑝濡쒗븯�뒗 �뒪�젅�뱶 �맂 媛꾨떒�븳 �뵆�젅�씠�뼱 �겢�옒�뒪�엯�땲�떎.
 * 
 * It has been successfully tested under J2SE 1.3.x, 1.4.x and 1.5.x.
 * J2SE 1.3.x, 1.4.x 諛� 1.5.x�뿉�꽌 �꽦怨듭쟻�쑝濡� �뀒�뒪�듃�릺�뿀�뒿�땲�떎.
 */
public class BasicPlayer implements BasicController, Runnable
{
    public static int EXTERNAL_BUFFER_SIZE = 4000 * 4;
    public static int SKIP_INACCURACY_SIZE = 1200;
    protected Thread m_thread = null;
    protected Object m_dataSource;
    protected AudioInputStream m_encodedaudioInputStream;
    protected int encodedLength = -1;
    protected AudioInputStream m_audioInputStream;
    protected AudioFileFormat m_audioFileFormat;
    protected SourceDataLine m_line;
    protected FloatControl m_gainControl;
    protected FloatControl m_panControl;
    protected String m_mixerName = null;
    private int m_lineCurrentBufferSize = -1;
    private int lineBufferSize = -1;
    private long threadSleep = -1;
//    private static Log log = LogFactory.getLog(BasicPlayer.class);
    /**
     * These variables are used to distinguish stopped, paused, playing states.
     * �씠 蹂��닔�뒗 �젙吏�, �씪�떆 �젙吏�, �옱�깮 �긽�깭瑜� 援щ퀎�븯�뒗 �뜲 �궗�슜�맗�땲�떎.
     * 
     * We need them to control Thread.
     * Thread瑜� �젣�뼱�븯湲� �쐞�빐 洹멸쾬�뱾�씠 �븘�슂�빀�땲�떎.
     */
    public static final int UNKNOWN = -1;
    public static final int PLAYING = 0;
    public static final int PAUSED = 1;
    public static final int STOPPED = 2;
    public static final int OPENED = 3;
    public static final int SEEKING = 4;
    private int m_status = UNKNOWN;
    // Listeners to be notified.
    private Collection m_listeners = null;
    private Map empty_map = new HashMap();

    /**
     * Constructs a Basic Player.
     */
    public BasicPlayer()
    {
        m_dataSource = null;
        m_listeners = new ArrayList();
        reset();
    }

    protected void reset()
    {
        m_status = UNKNOWN;
        if (m_audioInputStream != null)
        {
            synchronized (m_audioInputStream)
            {
                closeStream();
            }
        }
        m_audioInputStream = null;
        m_audioFileFormat = null;
        m_encodedaudioInputStream = null;
        encodedLength = -1;
        if (m_line != null)
        {
            m_line.stop();
            m_line.close();
            m_line = null;
        }
        m_gainControl = null;
        m_panControl = null;
    }

    /**
     * Add listener to be notified.
     * �넻吏� �븷 由ъ뒪�꼫瑜� 異붽��븯�떗�떆�삤.
     * @param bpl
     */
    public void addBasicPlayerListener(BasicPlayerListener bpl)
    {
        m_listeners.add(bpl);
    }

    /**
     * Return registered listeners.
     * �벑濡� �맂 由ъ뒪�꼫瑜� 諛섑솚�빀�땲�떎.
     * @return
     */
    public Collection getListeners()
    {
        return m_listeners;
    }

    /**
     * Remove registered listener.
     * �벑濡� �맂 由ъ뒪�꼫瑜� �젣嫄고븯�떗�떆�삤.
     * @param bpl
     */
    public void removeBasicPlayerListener(BasicPlayerListener bpl)
    {
        if (m_listeners != null)
        {
            m_listeners.remove(bpl);
        }
    }

    /**
     * Set SourceDataLine buffer size. It affects audio latency.
     * SourceDataLine 踰꾪띁 �겕湲곕�� �꽕�젙�븯�떗�떆�삤. �삤�뵒�삤 ��湲� �떆媛꾩뿉 �쁺�뼢�쓣以띾땲�떎.
     * (the delay between line.write(data) and real sound).
     * (line.write (data)�� �떎�젣 �궗�슫�뱶 �궗�씠�쓽 吏��뿰).
     * Minimum value should be over 10000 bytes.
     * 理쒖냼媛믪� 10000 諛붿씠�듃瑜� 珥덇낵�빐�빞�빀�땲�떎.
     * @param size -1 means maximum buffer size available.
     */
    public void setLineBufferSize(int size)
    {
        lineBufferSize = size;
    }

    /**
     * Return SourceDataLine buffer size.
     * SourceDataLine 踰꾪띁 �겕湲곕�� 由ы꽩�빀�땲�떎.
     * @return -1 maximum buffer size.
     */
    public int getLineBufferSize()
    {
        return lineBufferSize;
    }
    
    /**
     * Return SourceDataLine current buffer size.
     * SourceDataLine �쁽�옱 踰꾪띁 �겕湲곕�� 由ы꽩�빀�땲�떎.
     * @return
     */
    public int getLineCurrentBufferSize()
    {
        return m_lineCurrentBufferSize;
    }

    /**
     * Set thread sleep time.
     * �뒪�젅�뱶 �젅�쟾 �떆媛꾩쓣 �꽕�젙�븯�떗�떆�삤.
     * Default is -1 (no sleep time).
     * @param time in milliseconds.
     */
    public void setSleepTime(long time)
    {
        threadSleep = time;
    }

    /**
     * Return thread sleep time in milliseconds.
     * �뒪�젅�뱶 �젅�쟾 �떆媛� (諛�由� 珥�)�쓣 諛섑솚�빀�땲�떎.
     * @return -1 means no sleep time.
     */
    public long getSleepTime()
    {
        return threadSleep;
    }

    /**
     * Returns BasicPlayer status.
     * BasicPlayer �긽�깭瑜� 諛섑솚�빀�땲�떎.
     * @return status
     */
    public int getStatus()
    {
        return m_status;
    }

    /**
     * Open file to play.
     */
    public void open(File file) throws BasicPlayerException
    {
//        log.info("open(" + file + ")");
        if (file != null)
        {
            m_dataSource = file;
            initAudioInputStream();
        }
    }

    /**
     * Open URL to play.
     */
    public void open(URL url) throws BasicPlayerException
    {
//        log.info("open(" + url + ")");
        if (url != null)
        {
            m_dataSource = url;
            initAudioInputStream();
        }
    }

    /**
     * Open inputstream to play.
     */
    public void open(InputStream inputStream) throws BasicPlayerException
    {
//        log.info("open(" + inputStream + ")");
        if (inputStream != null)
        {
            m_dataSource = inputStream;
            initAudioInputStream();
        }
    }

    /**
     * Inits AudioInputStream and AudioFileFormat from the data source.
     * �뜲�씠�꽣 �냼�뒪�뿉�꽌 AudioInputStream 諛� AudioFileFormat�쓣 珥덇린�솕�빀�땲�떎.
     * @throws BasicPlayerException
     */
    protected void initAudioInputStream() throws BasicPlayerException
    {
        try
        {
            reset();
            notifyEvent(BasicPlayerEvent.OPENING, getEncodedStreamPosition(), -1, m_dataSource);
            if (m_dataSource instanceof URL)
            {
                initAudioInputStream((URL) m_dataSource);
            }
            else if (m_dataSource instanceof File)
            {
                initAudioInputStream((File) m_dataSource);
            }
            else if (m_dataSource instanceof InputStream)
            {
                initAudioInputStream((InputStream) m_dataSource);
            }
            createLine();
            // Notify listeners with AudioFileFormat properties.
            // AudioFileFormat �냽�꽦�쑝濡� 由ъ뒪�꼫�뿉寃� �븣由쎈땲�떎.
            Map properties = null;
            if (m_audioFileFormat instanceof TAudioFileFormat)
            {
                // Tritonus SPI compliant audio file format.
            	// Tritonus SPI �샇�솚 �삤�뵒�삤 �뙆�씪 �삎�떇.
                properties = ((TAudioFileFormat) m_audioFileFormat).properties();
                // Clone the Map because it is not mutable.
                // 蹂�寃쏀븷 �닔 �뾾�쑝誘�濡� 留듭쓣 蹂듭젣�븯�떗�떆�삤.
                properties = deepCopy(properties);
            }
            else properties = new HashMap();
            // Add JavaSound properties.
            // Add JavaSound properties.
            if (m_audioFileFormat.getByteLength() > 0) properties.put("audio.length.bytes", new Integer(m_audioFileFormat.getByteLength()));
            if (m_audioFileFormat.getFrameLength() > 0) properties.put("audio.length.frames", new Integer(m_audioFileFormat.getFrameLength()));
            if (m_audioFileFormat.getType() != null) properties.put("audio.type", (m_audioFileFormat.getType().toString()));
            // Audio format.
            // �삤�뵒�삤 �삎�떇.
            AudioFormat audioFormat = m_audioFileFormat.getFormat();
            if (audioFormat.getFrameRate() > 0) properties.put("audio.framerate.fps", new Float(audioFormat.getFrameRate()));
            if (audioFormat.getFrameSize() > 0) properties.put("audio.framesize.bytes", new Integer(audioFormat.getFrameSize()));
            if (audioFormat.getSampleRate() > 0) properties.put("audio.samplerate.hz", new Float(audioFormat.getSampleRate()));
            if (audioFormat.getSampleSizeInBits() > 0) properties.put("audio.samplesize.bits", new Integer(audioFormat.getSampleSizeInBits()));
            if (audioFormat.getChannels() > 0) properties.put("audio.channels", new Integer(audioFormat.getChannels()));
            if (audioFormat instanceof TAudioFormat)
            {
                // Tritonus SPI compliant audio format.
            	// Tritonus SPI �샇�솚 �삤�뵒�삤 �삎�떇.
                Map addproperties = ((TAudioFormat) audioFormat).properties();
                properties.putAll(addproperties);
            }
            // Add SourceDataLine
            // SourceDataLine 異붽�
            properties.put("basicplayer.sourcedataline", m_line);
            Iterator it = m_listeners.iterator();
            while (it.hasNext())
            {
                BasicPlayerListener bpl = (BasicPlayerListener) it.next();
                bpl.opened(m_dataSource, properties);
            }
            m_status = OPENED;
            notifyEvent(BasicPlayerEvent.OPENED, getEncodedStreamPosition(), -1, null);
        }
        catch (LineUnavailableException e)
        {
            throw new BasicPlayerException(e);
        }
        catch (UnsupportedAudioFileException e)
        {
            throw new BasicPlayerException(e);
        }
        catch (IOException e)
        {
            throw new BasicPlayerException(e);
        }
    }

    /**
     * Inits Audio ressources from file.
     * Inits Audio�뒗 �뙆�씪�뿉�꽌 由ъ냼�뒪瑜� �젣怨듯빀�땲�떎.
     */
    protected void initAudioInputStream(File file) throws UnsupportedAudioFileException, IOException
    {
        m_audioInputStream = AudioSystem.getAudioInputStream(file);
        m_audioFileFormat = AudioSystem.getAudioFileFormat(file);
    }

    /**
     * Inits Audio ressources from URL.
     */
    protected void initAudioInputStream(URL url) throws UnsupportedAudioFileException, IOException
    {
        m_audioInputStream = AudioSystem.getAudioInputStream(url);
        m_audioFileFormat = AudioSystem.getAudioFileFormat(url);
    }

    /**
     * Inits Audio ressources from InputStream.
     */
    protected void initAudioInputStream(InputStream inputStream) throws UnsupportedAudioFileException, IOException
    {
        m_audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        m_audioFileFormat = AudioSystem.getAudioFileFormat(inputStream);
    }

    /**
     * Inits Audio ressources from AudioSystem.<br>
     */
    protected void initLine() throws LineUnavailableException
    {
//        log.info("initLine()");
        if (m_line == null) createLine();
        if (!m_line.isOpen())
        {
            openLine();
        }
        else
        {
            AudioFormat lineAudioFormat = m_line.getFormat();
            AudioFormat audioInputStreamFormat = m_audioInputStream == null ? null : m_audioInputStream.getFormat();
            if (!lineAudioFormat.equals(audioInputStreamFormat))
            {
                m_line.close();
                openLine();
            }
        }
    }

    /**
     * Inits a DateLine.<br>
     * DateLine�쓣 珥덇린�솕�빀�땲�떎.
     * 
     * We check if the line supports Gain and Pan controls.
     * �씪�씤�씠 寃뚯씤 諛� �뙩 而⑦듃濡ㅼ쓣 吏��썝�븯�뒗吏� �솗�씤�빀�땲�떎.
     *
     * From the AudioInputStream, i.e. from the sound file, we
     * AudioInputStream, 利� �궗�슫�뱶 �뙆�씪�뿉�꽌
     * fetch information about the format of the audio data. These
     * �삤�뵒�삤 �뜲�씠�꽣 �삎�떇�뿉 ���븳 �젙蹂대�� 媛��졇�샃�땲�떎. �씠�뱾
     * information include the sampling frequency, the number of
     * �젙蹂대뒗 �깦�뵆留� 二쇳뙆�닔,
     * channels and the size of the samples. There information
     * 梨꾨꼸 諛� �깦�뵆�쓽 �겕湲�. �젙蹂닿� �엳�뒿�땲�떎
     * are needed to ask JavaSound for a suitable output line
     * �쟻�젅�븳 異쒕젰 �씪�씤�쓣 JavaSound�뿉 �슂援ы븷 �븘�슂媛��엳�떎
     * for this audio file.
     * �씠 �삤�뵒�삤 �뙆�씪�쓽 寃쎌슦
     * Furthermore, we have to give JavaSound a hint about how
     * �삉�븳 JavaSound�뿉 諛⑸쾿�뿉 ���븳 �엺�듃瑜� �젣怨듯빐�빞�빀�땲�떎.
     * big the internal buffer for the line should be. Here,
     * �씪�씤�쓽 �궡遺� 踰꾪띁媛� 而ㅼ빞�빀�땲�떎. �뿬湲�,
     * we say AudioSystem.NOT_SPECIFIED, signaling that we don't
     * �슦由щ뒗 AudioSystem.NOT_SPECIFIED�씪怨� 留먰븯硫�,
     * care about the exact size. JavaSound will use some default
     * �젙�솗�븳 �겕湲곗뿉 �떊寃� �벐�떗�떆�삤. JavaSound�뒗 �빟媛꾩쓽 湲곕낯媛믪쓣 �궗�슜�빀�땲�떎
     * value for the buffer size.
     * 踰꾪띁 �겕湲� 媛�.
     */
    protected void createLine() throws LineUnavailableException
    {
//        log.info("Create Line");
        if (m_line == null)
        {
            AudioFormat sourceFormat = m_audioInputStream.getFormat();
//            log.info("Create Line : Source format : " + sourceFormat.toString());
            int nSampleSizeInBits = sourceFormat.getSampleSizeInBits();
            if (nSampleSizeInBits <= 0) nSampleSizeInBits = 16;
            if ((sourceFormat.getEncoding() == AudioFormat.Encoding.ULAW) || (sourceFormat.getEncoding() == AudioFormat.Encoding.ALAW)) nSampleSizeInBits = 16;
            if (nSampleSizeInBits != 8) nSampleSizeInBits = 16;
            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), nSampleSizeInBits, sourceFormat.getChannels(), sourceFormat.getChannels() * (nSampleSizeInBits / 8), sourceFormat.getSampleRate(), false);
//            log.info("Create Line : Target format: " + targetFormat);
            // Keep a reference on encoded stream to progress notification.
            // �븣由쇱쓣 吏꾪뻾�븯�젮硫� �씤肄붾뵫 �맂 �뒪�듃由쇱뿉 ���븳 李몄“瑜� �쑀吏��븯�떗�떆�삤.
            m_encodedaudioInputStream = m_audioInputStream;
            try
            {
                // Get total length in bytes of the encoded stream.
            	// �씤肄붾뵫 �맂 �뒪�듃由쇱쓽 珥� 湲몄씠瑜� 諛붿씠�듃 �떒�쐞濡� 媛��졇�샃�땲�떎.
                encodedLength = m_encodedaudioInputStream.available();
            }
            catch (IOException e)
            {
//                log.error("Cannot get m_encodedaudioInputStream.available()", e);
            }
            // Create decoded stream.
            // �뵒肄붾뵫 �맂 �뒪�듃由쇱쓣 留뚮벊�땲�떎.
            m_audioInputStream = AudioSystem.getAudioInputStream(targetFormat, m_audioInputStream);
            AudioFormat audioFormat = m_audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            Mixer mixer = getMixer(m_mixerName);
            if (mixer != null)
            {
//                log.info("Mixer : "+mixer.getMixerInfo().toString());
                m_line = (SourceDataLine) mixer.getLine(info);
            }
            else 
            {
                m_line = (SourceDataLine) AudioSystem.getLine(info);
                m_mixerName = null;
            }
//            log.info("Line : " + m_line.toString());
//            log.debug("Line Info : " + m_line.getLineInfo().toString());
//            log.debug("Line AudioFormat: " + m_line.getFormat().toString());
        }
    }

    /**
     * Opens the line.
     * �씪�씤�쓣 �뿽�땲�떎.
     */
    protected void openLine() throws LineUnavailableException
    {
        if (m_line != null)
        {
            AudioFormat audioFormat = m_audioInputStream.getFormat();
            int buffersize = lineBufferSize;
            if (buffersize <= 0) buffersize = m_line.getBufferSize();
            m_lineCurrentBufferSize = buffersize;
            m_line.open(audioFormat, buffersize);
//            log.info("Open Line : BufferSize=" + buffersize);
            /*-- Display supported controls --*/
            Control[] c = m_line.getControls();
            for (int p = 0; p < c.length; p++)
            {
//                log.debug("Controls : " + c[p].toString());
            }
            /*-- Is Gain Control supported ? --*/
            if (m_line.isControlSupported(FloatControl.Type.MASTER_GAIN))
            {
                m_gainControl = (FloatControl) m_line.getControl(FloatControl.Type.MASTER_GAIN);
//                log.info("Master Gain Control : [" + m_gainControl.getMinimum() + "," + m_gainControl.getMaximum() + "] " + m_gainControl.getPrecision());
            }
            /*-- Is Pan control supported ? --*/
            if (m_line.isControlSupported(FloatControl.Type.PAN))
            {
                m_panControl = (FloatControl) m_line.getControl(FloatControl.Type.PAN);
//                log.info("Pan Control : [" + m_panControl.getMinimum() + "," + m_panControl.getMaximum() + "] " + m_panControl.getPrecision());
            }
        }
    }

    /**
     * Stops the playback.<br>
     * �옱�깮�쓣 以묒��빀�땲�떎.
     *
     * Player Status = STOPPED.<br>
     * �뵆�젅�씠�뼱 �긽�깭 = STOPPED. <br>
     * Thread should free Audio ressources.
     * �뒪�젅�뱶�뒗 �삤�뵒�삤 由ъ냼�뒪瑜� �빐�젣�빐�빞�빀�땲�떎.
     */
    protected void stopPlayback()
    {
        if ((m_status == PLAYING) || (m_status == PAUSED))
        {
            if (m_line != null)
            {
                m_line.flush();
                m_line.stop();
            }
            m_status = STOPPED;
            notifyEvent(BasicPlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
            synchronized (m_audioInputStream)
            {
                closeStream();
            }
//            log.info("stopPlayback() completed");
        }
    }

    /**
     * Pauses the playback.<br>
     * �옱�깮�쓣 �씪�떆 �젙吏��빀�땲�떎. <br>
     * Player Status = PAUSED.
     * �뵆�젅�씠�뼱 �긽�깭 = �씪�떆 以묒��맖.
     */
    protected void pausePlayback()
    {
        if (m_line != null)
        {
            if (m_status == PLAYING)
            {
                m_line.flush();
                m_line.stop();
                m_status = PAUSED;
//                log.info("pausePlayback() completed");
                notifyEvent(BasicPlayerEvent.PAUSED, getEncodedStreamPosition(), -1, null);
            }
        }
    }

    /**
     * Resumes the playback.<br>
     * �옱�깮�쓣 �떎�떆 �떆�옉�빀�땲�떎. <br>
     * Player Status = PLAYING.
     * �뵆�젅�씠�뼱 �긽�깭 = �옱�깮 以�.
     */
    protected void resumePlayback()
    {
        if (m_line != null)
        {
            if (m_status == PAUSED)
            {
                m_line.start();
                m_status = PLAYING;
//                log.info("resumePlayback() completed");
                notifyEvent(BasicPlayerEvent.RESUMED, getEncodedStreamPosition(), -1, null);
            }
        }
    }

    /**
     * Starts playback.
     * �옱�깮�쓣 �떆�옉�빀�땲�떎.
     */
    protected void startPlayback() throws BasicPlayerException
    {
        if (m_status == STOPPED) initAudioInputStream();
        if (m_status == OPENED)
        {
//            log.info("startPlayback called");
            if (!(m_thread == null || !m_thread.isAlive()))
            {
//                log.info("WARNING: old thread still running!!");
                int cnt = 0;
                while (m_status != OPENED)
                {
                    try
                    {
                        if (m_thread != null)
                        {
//                            log.info("Waiting ... " + cnt);
                            cnt++;
                            Thread.sleep(1000);
                            if (cnt > 2)
                            {
                                m_thread.interrupt();
                            }
                        }
                    }
                    catch (InterruptedException e)
                    {
                        throw new BasicPlayerException(BasicPlayerException.WAITERROR, e);
                    }
                }
            }
            // Open SourceDataLine.
            // SourceDataLine�쓣�뿬�떗�떆�삤.
            try
            {
                initLine();
            }
            catch (LineUnavailableException e)
            {
                throw new BasicPlayerException(BasicPlayerException.CANNOTINITLINE, e);
            }
//            log.info("Creating new thread");
            m_thread = new Thread(this, "BasicPlayer");
            m_thread.start();
            
            if (m_line != null)
            {
                m_line.start();
                m_status = PLAYING;
                notifyEvent(BasicPlayerEvent.PLAYING, getEncodedStreamPosition(), -1, null);
            }
        }
    }

    /**
     * Main loop.
     * 硫붿씤 猷⑦봽.
     *
     * Player Status == STOPPED || SEEKING => End of Thread + Freeing Audio Ressources.<br>
     * �뵆�젅�씠�뼱 �긽�깭 == 以묒��맖 || 寃��깋 => �뒪�젅�뱶 醫낅즺 + �삤�뵒�삤 由ъ냼�뒪 �빐�젣. <br>
     * Player Status == PLAYING => Audio stream data sent to Audio line.<br>
     * �뵆�젅�씠�뼱 �긽�깭 == �옱�깮 以� => �삤�뵒�삤 �뒪�듃由� �뜲�씠�꽣媛� �삤�뵒�삤 �씪�씤�쑝濡� �쟾�넚�릺�뿀�뒿�땲�떎. <br>
     * Player Status == PAUSED => Waiting for another status.
     * Player Status == PAUSED => �떎瑜� �긽�깭瑜� 湲곕떎由щ뒗 以묒엯�땲�떎.
     */
    public void run()
    {
//        log.info("Thread Running");
        int nBytesRead = 1;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
        // Lock stream while playing.
        synchronized (m_audioInputStream)
        {
            // Main play/pause loop.
        	// 硫붿씤 �옱�깮 / �씪�떆 �젙吏� 猷⑦봽.
            while ((nBytesRead != -1) && (m_status != STOPPED) && (m_status != SEEKING) && (m_status != UNKNOWN))
            {
                if (m_status == PLAYING)
                {
                    // Play.
                    try
                    {
                        nBytesRead = m_audioInputStream.read(abData, 0, abData.length);
                        if (nBytesRead >= 0)
                        {
                            byte[] pcm = new byte[nBytesRead];
                            System.arraycopy(abData, 0, pcm, 0, nBytesRead);
//                            if (m_line.available() >= m_line.getBufferSize()) log.debug("Underrun : "+m_line.available()+"/"+m_line.getBufferSize());
                            int nBytesWritten = m_line.write(abData, 0, nBytesRead);
                            // Compute position in bytes in encoded stream.
                            // �씤肄붾뵫 �맂 �뒪�듃由쇱뿉�꽌 諛붿씠�듃 �떒�쐞�쓽 怨꾩궛 �쐞移�
                            int nEncodedBytes = getEncodedStreamPosition();
                            // Notify listeners
                            // 泥�痍⑥옄�뿉寃� �븣由�
                            Iterator it = m_listeners.iterator();
                            while (it.hasNext())
                            {
                                BasicPlayerListener bpl = (BasicPlayerListener) it.next();
                                if (m_audioInputStream instanceof PropertiesContainer)
                                {
                                    // Pass audio parameters such as instant bitrate, ...
                                	// 利됱꽍 鍮꾪듃 �쟾�넚瑜좉낵 媛숈� �삤�뵒�삤 留ㅺ컻 蹂��닔瑜� �쟾�떖�빀�땲�떎.
                                    Map properties = ((PropertiesContainer) m_audioInputStream).properties();
                                    bpl.progress(nEncodedBytes, m_line.getMicrosecondPosition(), pcm, properties);
                                }
                                else bpl.progress(nEncodedBytes, m_line.getMicrosecondPosition(), pcm, empty_map);
                            }
                        }
                    }
                    catch (IOException e)
                    {
//                        log.error("Thread cannot run()", e);
                        m_status = STOPPED;
                        notifyEvent(BasicPlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
                    }
                    // Nice CPU usage.
                    // CPU �궗�슜�웾�씠 醫뗭뒿�땲�떎.
                    if (threadSleep > 0)
                    {
                        try
                        {
                            Thread.sleep(threadSleep);
                        }
                        catch (InterruptedException e)
                        {
//                            log.error("Thread cannot sleep(" + threadSleep + ")", e);
                        }
                    }
                }
                else
                {
                    // Pause
                	// 以묒�
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
//                        log.error("Thread cannot sleep(1000)", e);
                    }
                }
            }
            // Free audio resources.
            // �삤�뵒�삤 �옄猷뚮�� �떕湲�
            if (m_line != null)
            {
                m_line.drain();
                m_line.stop();
                m_line.close();
                m_line = null;
            }
            // Notification of "End Of Media"
            // "誘몃뵒�뼱 �걹"�븣由�
            if (nBytesRead == -1)
            {
                notifyEvent(BasicPlayerEvent.EOM, getEncodedStreamPosition(), -1, null);
            }
            // Close stream.
            // �뒪�듃由쇱쓣 �떕�뒿�땲�떎.
            closeStream();
        }
        m_status = STOPPED;
        notifyEvent(BasicPlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
//        log.info("Thread completed");
    }

    /**
     * Skip bytes in the File inputstream.
     * �뙆�씪 �엯�젰 �뒪�듃由쇱뿉�꽌 諛붿씠�듃瑜� 嫄대꼫 �쐛�땲�떎.
     * It will skip N frames matching to bytes, so it will never skip given bytes length exactly.
     * 諛붿씠�듃�� �뗢�뗭씪移섑븯�뒗 N 媛쒖쓽 �봽�젅�엫�쓣 嫄대꼫 �쎇誘�濡� 二쇱뼱吏� 諛붿씠�듃 湲몄씠瑜� �젙�솗�븯寃� 嫄대꼫 �쎇吏� �븡�뒿�땲�떎.
     * @param bytes
     * @return value>0 for File and value=0 for URL and InputStream
     * @throws BasicPlayerException
     */
    protected long skipBytes(long bytes) throws BasicPlayerException
    {
        long totalSkipped = 0;
        if (m_dataSource instanceof File)
        {
//            log.info("Bytes to skip : " + bytes);
            int previousStatus = m_status;
            m_status = SEEKING;
            long skipped = 0;
            try
            {
                synchronized (m_audioInputStream)
                {
                    notifyEvent(BasicPlayerEvent.SEEKING, getEncodedStreamPosition(), -1, null);
                    initAudioInputStream();
                    if (m_audioInputStream != null)
                    {
                        // Loop until bytes are really skipped.
                        while (totalSkipped < (bytes - SKIP_INACCURACY_SIZE))
                        {
                            skipped = m_audioInputStream.skip(bytes - totalSkipped);
                            if (skipped == 0) break;
                            totalSkipped = totalSkipped + skipped;
//                            log.info("Skipped : " + totalSkipped + "/" + bytes);
                            if (totalSkipped == -1) throw new BasicPlayerException(BasicPlayerException.SKIPNOTSUPPORTED);
                        }
                    }
                }
                notifyEvent(BasicPlayerEvent.SEEKED, getEncodedStreamPosition(), -1, null);
                m_status = OPENED;
                if (previousStatus == PLAYING) startPlayback();
                else if (previousStatus == PAUSED)
                {
                    startPlayback();
                    pausePlayback();
                }
            }
            catch (IOException e)
            {
                throw new BasicPlayerException(e);
            }
        }
        return totalSkipped;
    }

    /**
     * Notify listeners about a BasicPlayerEvent.
     * 泥�痍⑥옄�뿉寃� BasicPlayerEvent�뿉 ���빐 �넻吏��븯�떗�떆�삤.
     * @param code event code.
     * @param position in the stream when the event occurs.
     */
    protected void notifyEvent(int code, int position, double value, Object description)
    {
        BasicPlayerEventLauncher trigger = new BasicPlayerEventLauncher(code, position, value, description, new ArrayList(m_listeners), this);
        trigger.start();
    }

    protected int getEncodedStreamPosition()
    {
        int nEncodedBytes = -1;
        if (m_dataSource instanceof File)
        {
            try
            {
                if (m_encodedaudioInputStream != null)
                {
                    nEncodedBytes = encodedLength - m_encodedaudioInputStream.available();
                }
            }
            catch (IOException e)
            {
                //log.debug("Cannot get m_encodedaudioInputStream.available()",e);
            }
        }
        return nEncodedBytes;
    }

    protected void closeStream()
    {
        // Close stream.
        try
        {
            if (m_audioInputStream != null)
            {
                m_audioInputStream.close();
//                log.info("Stream closed");
            }
        }
        catch (IOException e)
        {
//            log.info("Cannot close stream", e);
        }
    }

    /**
     * Returns true if Gain control is supported.
     */
    public boolean hasGainControl()
    {
        if (m_gainControl == null)
        {
            // Try to get Gain control again (to support J2SE 1.5)
            if ( (m_line != null) && (m_line.isControlSupported(FloatControl.Type.MASTER_GAIN))) m_gainControl = (FloatControl) m_line.getControl(FloatControl.Type.MASTER_GAIN);
        }
        return m_gainControl != null;
    }

    /**
     * Returns Gain value.
     */
    public float getGainValue()
    {
        if (hasGainControl())
        {
            return m_gainControl.getValue();
        }
        else
        {
            return 0.0F;
        }
    }

    /**
     * Gets max Gain value.
     */
    public float getMaximumGain()
    {
        if (hasGainControl())
        {
            return m_gainControl.getMaximum();
        }
        else
        {
            return 0.0F;
        }
    }

    /**
     * Gets min Gain value.
     */
    public float getMinimumGain()
    {
        if (hasGainControl())
        {
            return m_gainControl.getMinimum();
        }
        else
        {
            return 0.0F;
        }
    }

    /**
     * Returns true if Pan control is supported.
     */
    public boolean hasPanControl()
    {
        if (m_panControl == null)
        {
            // Try to get Pan control again (to support J2SE 1.5)
        	// �뙩 �젣�뼱瑜� �떎�떆 �떆�룄�븯�떗�떆�삤 (J2SE 1.5 吏��썝).
            if ((m_line != null)&& (m_line.isControlSupported(FloatControl.Type.PAN))) m_panControl = (FloatControl) m_line.getControl(FloatControl.Type.PAN);
        }
        return m_panControl != null;
    }

    /**
     * Returns Pan precision.
     */
    public float getPrecision()
    {
        if (hasPanControl())
        {
            return m_panControl.getPrecision();
        }
        else
        {
            return 0.0F;
        }
    }

    /**
     * Returns Pan value.
     */
    public float getPan()
    {
        if (hasPanControl())
        {
            return m_panControl.getValue();
        }
        else
        {
            return 0.0F;
        }
    }

    /**
     * Deep copy of a Map.
     * Map�쓽 源딆� 蹂듭젣.
     * @param src
     * @return
     */
    protected Map deepCopy(Map src)
    {
        HashMap map = new HashMap();
        if (src != null)
        {
            Iterator it = src.keySet().iterator();
            while (it.hasNext())
            {
                Object key = it.next();
                Object value = src.get(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * @see javazoom.jlgui.basicplayer.BasicController#seek(long)
     */
    public long seek(long bytes) throws BasicPlayerException
    {
        return skipBytes(bytes);
    }

    /**
     * @see javazoom.jlgui.basicplayer.BasicController#play()
     */
    public void play() throws BasicPlayerException
    {
        startPlayback();
    }

    /**
     * @see javazoom.jlgui.basicplayer.BasicController#stop()
     */
    public void stop() throws BasicPlayerException
    {
        stopPlayback();
    }

    /**
     * @see javazoom.jlgui.basicplayer.BasicController#pause()
     */
    public void pause() throws BasicPlayerException
    {
        pausePlayback();
    }

    /**
     * @see javazoom.jlgui.basicplayer.BasicController#resume()
     */
    public void resume() throws BasicPlayerException
    {
        resumePlayback();
    }

    /**
     * Sets Pan value.
     * �뙩 媛믪쓣 �꽕�젙�빀�땲�떎.
     * Line should be opened before calling this method.
     * �씠 硫붿냼�뱶瑜� �샇異쒗븯湲� �쟾�뿉 �뻾�쓣 �뿴�뼱�빞�빀�땲�떎.
     * Linear scale : -1.0 <--> +1.0
     */
    public void setPan(double fPan) throws BasicPlayerException
    {
        if (hasPanControl())
        {
//            log.debug("Pan : " + fPan);
            m_panControl.setValue((float) fPan);
            notifyEvent(BasicPlayerEvent.PAN, getEncodedStreamPosition(), fPan, null);
        }
        else throw new BasicPlayerException(BasicPlayerException.PANCONTROLNOTSUPPORTED);
    }

    /**
     * Sets Gain value.
     * 寃뚯씤 媛믪쓣 �꽕�젙�빀�땲�떎.
     * Line should be opened before calling this method.
     * �씠 硫붿냼�뱶瑜� �샇異쒗븯湲� �쟾�뿉 �뻾�쓣 �뿴�뼱�빞�빀�땲�떎.
     * Linear scale 0.0  <-->  1.0
     * Threshold Coef. : 1/2 to avoid saturation.
     */
    public void setGain(double fGain) throws BasicPlayerException
    {
        if (hasGainControl())
        {
            double minGainDB = getMinimumGain();
            double ampGainDB = ((10.0f / 20.0f) * getMaximumGain()) - getMinimumGain();
            double cste = Math.log(10.0) / 20;
            double valueDB = minGainDB + (1 / cste) * Math.log(1 + (Math.exp(cste * ampGainDB) - 1) * fGain);
//            log.debug("Gain : " + valueDB);
            m_gainControl.setValue((float) valueDB);
            notifyEvent(BasicPlayerEvent.GAIN, getEncodedStreamPosition(), fGain, null);
        }
        else throw new BasicPlayerException(BasicPlayerException.GAINCONTROLNOTSUPPORTED);
    }
    
    public List getMixers()
    {
        ArrayList mixers = new ArrayList();
        Mixer.Info[] mInfos = AudioSystem.getMixerInfo();
        if (mInfos != null)
        {
            for (int i = 0; i < mInfos.length; i++)
            {
                Line.Info lineInfo = new Line.Info(SourceDataLine.class);
                Mixer mixer = AudioSystem.getMixer(mInfos[i]);
                if (mixer.isLineSupported(lineInfo))
                {
                    mixers.add(mInfos[i].getName());
                }
            }            
        }
        return mixers;        
    }
    
    public Mixer getMixer(String name)
    {
        Mixer mixer = null;
        if (name != null)
        {
            Mixer.Info[] mInfos = AudioSystem.getMixerInfo();
            if (mInfos != null)
            {
                for (int i = 0; i < mInfos.length; i++)
                {
                    if (mInfos[i].getName().equals(name))
                    {
                        mixer = AudioSystem.getMixer(mInfos[i]);
                        break;
                    }
                }            
            }            
        }
        return mixer;
    }
    
    public String getMixerName()
    {
        return m_mixerName;
    }
    
    public void setMixerName(String name)
    {
        m_mixerName = name;
    }
}
