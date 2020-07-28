/*
 * BasicPlayerEvent.
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

/**
 * This class implements player events.
 * 이 클래스는 플레이어 이벤트를 구현합니다. 
 */
public class BasicPlayerEvent
{
    public static final int UNKNOWN = -1;
    public static final int OPENING = 0;
    public static final int OPENED = 1;
    public static final int PLAYING = 2;
    public static final int STOPPED = 3;
    public static final int PAUSED = 4;
    public static final int RESUMED = 5;
    public static final int SEEKING = 6;
    public static final int SEEKED = 7;
    public static final int EOM = 8;
    public static final int PAN = 9;
    public static final int GAIN = 10;
    private int code = UNKNOWN;
    private int position = -1;
    private double value = -1.0;
    private Object source = null;
    private Object description = null;

    /**
     * Constructor
     * @param source of the event
     * 이벤트의 @param 소스
     * @param code of the envent
     * 발명의 @ 매개 변수 코드
     * @param position optional stream position
     * @param 위치 선택적 스트림 위치
     * @param value opitional control value
     * @param 가치 선택 통제 가치
     * @param desc optional description
     * @param desc 선택적 설명
     */
    public BasicPlayerEvent(Object source, int code, int position, double value, Object desc)
    {
        this.value = value;
        this.position = position;
        this.source = source;
        this.code = code;
        this.description = desc;
    }

    /**
     * Return code of the event triggered.
     * 트리거 된 이벤트의 리턴 코드.
     * @return
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Return position in the stream when event occured.
     * 이벤트가 발생했을 때 스트림에서 위치를 반환합니다.
     * @return
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * Return value related to event triggered.
     * 트리거 된 이벤트와 관련된 반환 값. 
     * @return
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Return description.
     * 설명을 반환합니다.
     * @return
     */
    public Object getDescription()
    {
        return description;
    }

    public Object getSource()
    {
        return source;
    }

    public String toString()
    {
        if (code == OPENED) return "OPENED:" + position;
        else if (code == OPENING) return "OPENING:" + position + ":" + description;
        else if (code == PLAYING) return "PLAYING:" + position;
        else if (code == STOPPED) return "STOPPED:" + position;
        else if (code == PAUSED) return "PAUSED:" + position;
        else if (code == RESUMED) return "RESUMED:" + position;
        else if (code == SEEKING) return "SEEKING:" + position;
        else if (code == SEEKED) return "SEEKED:" + position;
        else if (code == EOM) return "EOM:" + position;
        else if (code == PAN) return "PAN:" + value;
        else if (code == GAIN) return "GAIN:" + value;
        else return "UNKNOWN:" + position;
    }
}
