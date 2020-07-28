/*
 * BasicPlayerListener.
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

import java.util.Map;

/**
 * This interface defines callbacks methods that will be notified
 * for all registered BasicPlayerListener of BasicPlayer.<br>
 * 이 인터페이스는 등록 된 모든 BasicPlayer의 BasicPlayerListener에 대해 통지 될 콜백 메소드를 정의합니다.
 */
public interface BasicPlayerListener
{
    /**
     * Open callback, stream is ready to play.
     * 콜백을 열면 스트림을 재생할 수 있습니다.
     *
     * properties map includes audio format dependant features such as
     * �냽�꽦 留듭뿉�뒗 �떎�쓬怨� 媛숈� �삤�뵒�삤 �삎�떇 醫낆냽 湲곕뒫�씠 �룷�븿�맗�땲�떎
     * bitrate, duration, frequency, channels, number of frames, vbr flag,
     * 鍮꾪듃 �쟾�넚瑜�, 吏��냽 �떆媛�, 二쇳뙆�닔, 梨꾨꼸, �봽�젅�엫 �닔, vbr �뵆�옒洹�,
     * id3v2/id3v1 (for MP3 only), comments (for Ogg Vorbis), ...  
     * id3v2 / id3v1 (MP3 �쟾�슜), �꽕紐� (Ogg Vorbis), ...
     *
     * @param stream could be File, URL or InputStream
     * @param stream�� File, URL �삉�뒗 InputStream �씪 �닔 �엳�뒿�땲�떎.
     * @param properties audio stream properties.
     * @param properties �삤�뵒�삤 �뒪�듃由� �냽�꽦.
     */
    public void opened(Object stream, Map properties);

    /**
     * Progress callback while playing.
     * �옱�깮�븯�뒗 �룞�븞 吏꾪뻾 肄쒕갚.
     * 
     * This method is called severals time per seconds while playing.
     * properties map includes audio format features such as
     * instant bitrate, microseconds position, current frame number, ... <br>
     * 이 메소드는 재생하는 동안 초당 몇 번 severals를 요청합니다. 
     * 속성 맵에는 인스턴트 비트 전송률, 마이크로 초 위치, 현재 프레임 번호와 같은 오디오 형식 기능이 포함됩니다.
     * 
     * @param bytesread from encoded stream. �씤肄붾뵫 �맂 �뒪�듃由쇱뿉�꽌 諛붿씠�듃瑜� �씫�뒿�땲�떎.
     * @param microseconds elapsed (<b>reseted after a seek !</b>). 留덉씠�겕濡� 珥덇� 寃쎄낵�뻽�뒿�땲�떎 (<b> 寃��깋 �썑 �옱�꽕�젙 </ b>).
     * @param pcmdata PCM samples. pcmdata PCM �깦�뵆.
     * @param properties audio stream parameters. �삤�뵒�삤 �뒪�듃由� �뙆�씪誘명꽣.
     */
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties);

    /**
     * Notification callback for basicplayer events such as opened, eom ... <br>
     * 기본 플레이어 이벤트에 대한 알림 콜백
     *  
     * @param event
     */
    public void stateUpdated(BasicPlayerEvent event);

    /**
     * A handle to the BasicPlayer, plugins may control the player through
     * the controller (play, stop, ...) <br>
     * BasicPlayer에 대한 핸들, 플러그인은 컨트롤러를 통해 플레이어를 제어 할 수 있습니다 (재생, 중지 등)
     * @param controller : a handle to the player  而⑦듃濡ㅻ윭 : �뵆�젅�씠�뼱 �빖�뱾
     */
    public void setController(BasicController controller);
}
