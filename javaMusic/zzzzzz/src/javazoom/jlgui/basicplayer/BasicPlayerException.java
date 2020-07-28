/*
 * BasicPlayerException.
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

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This class implements custom exception for basicplayer.
 * 이 클래스는 기본 플레이어에 대한 사용자 정의 예외를 구현합니다.
 */
public class BasicPlayerException extends Exception
{
    public static final String GAINCONTROLNOTSUPPORTED = "Gain control not supported";
    public static final String PANCONTROLNOTSUPPORTED = "Pan control not supported";
    public static final String WAITERROR = "Wait error";
    public static final String CANNOTINITLINE = "Cannot init line";
    public static final String SKIPNOTSUPPORTED = "Skip not supported";
    private Throwable cause = null;

    public BasicPlayerException()
    {
        super();
    }

    public BasicPlayerException(String msg)
    {
        super(msg);
    }

    public BasicPlayerException(Throwable cause)
    {
        super();
        this.cause = cause;
    }

    public BasicPlayerException(String msg, Throwable cause)
    {
        super(msg);
        this.cause = cause;
    }

    public Throwable getCause()
    {
        return cause;
    }

    /**
     * Returns the detail message string of this throwable. If it was
     * 던질 수있는 세부 메시지 문자열을 반환합니다. 그것이 있었다면
     * created with a null message, returns the following:
     * 널 메시지로 작성되면 다음을 리턴합니다.
     * (cause==null ? null : cause.toString()).
     */
    public String getMessage()
    {
        if (super.getMessage() != null)
        {
            return super.getMessage();
        }
        else if (cause != null)
        {
            return cause.toString();
        }
        else
        {
            return null;
        }
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream out)
    {
        synchronized (out)
        {
            PrintWriter pw = new PrintWriter(out, false);
            printStackTrace(pw);
            pw.flush();
        }
    }

    public void printStackTrace(PrintWriter out)
    {
        if (cause != null) cause.printStackTrace(out);
    }
}
