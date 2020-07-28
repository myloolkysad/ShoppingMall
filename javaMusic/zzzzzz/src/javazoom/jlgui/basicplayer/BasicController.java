/*
 * BasicController.
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
import java.io.InputStream;
import java.net.URL;

/**
 * This interface defines player controls available.
 * 이 인터페이스는 사용 가능한 플레이어 컨트롤을 정의합니다.
 */
public interface BasicController
{
    /**
     * Open inputstream to play.
     * 재생할 입력 스트림을 엽니 다.
     * 
     * @param in
     * @throws BasicPlayerException
     */
    public void open(InputStream in) throws BasicPlayerException;

    /**
     * Open file to play.
     * 재생할 파일을 엽니다.
     * @param file
     * @throws BasicPlayerException
     */
    public void open(File file) throws BasicPlayerException;

    /**
     * Open URL to play.
     * 재생할 URL을 엽니다.
     * @param url
     * @throws BasicPlayerException
     */
    public void open(URL url) throws BasicPlayerException;

    /**
     * Skip bytes.
     * 바이트를 건너 뜁니다.
     * @param bytes
     * @return bytes skipped according to audio frames constraint.
     * 오디오 프레임 제약 조건에 따라 @return 바이트를 건너 뜁니다.
     * @throws BasicPlayerException
     */
    public long seek(long bytes) throws BasicPlayerException;

    /**
     * Start playback.
     * 재생을 시작하십시오.
     * @throws BasicPlayerException
     */
    public void play() throws BasicPlayerException;

    /**
     * Stop playback.
     * 재생을 중지하십시오.
     * @throws BasicPlayerException
     */
    public void stop() throws BasicPlayerException;

    /**
     * Pause playback.
     * 재생을 일시중지하십시오. 
     * @throws BasicPlayerException
     */
    public void pause() throws BasicPlayerException;

    /**
     * Resume playback.
     * 재생을 다시 시작하십시오. 
     * @throws BasicPlayerException
     */
    public void resume() throws BasicPlayerException;

    /**
     * Sets Pan (Balance) value.
     * 팬 (밸런스) 값을 설정합니다.
     * Linear scale : -1.0 <--> +1.0
     * @param pan value from -1.0 to +1.0
     * @throws BasicPlayerException
     */
    public void setPan(double pan) throws BasicPlayerException;

    /**
     * Sets Gain value.
     * 게인 값을 설정합니다.
     * Linear scale 0.0  <-->  1.0
     * @param gain value from 0.0 to 1.0
     * @throws BasicPlayerException
     */
    public void setGain(double gain) throws BasicPlayerException;
}
