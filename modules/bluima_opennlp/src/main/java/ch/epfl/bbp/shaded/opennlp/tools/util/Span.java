///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002 Tom Morton
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.tools.util;

/** Class for storing start and end integer offsets.  
**/

public class Span implements Comparable {

  private int start;
  private int end;

  /** Constructs a new Span Object.
   * @param s start of span.
   * @param e end of span.
   */
  public Span(int s,int e) {
    start=s;
    end=e;
  }
  
  /** Return the start of a span.
   * @return the start of a span.
   **/
  public int getStart() {
    return(start);
  }
  
  /** Return the end of a span.
   * @return the end of a span.
   **/
  public int getEnd() {
    return(end);
  }

  /** 
   * Returns the length of this span.
   * @return the length of the span.
   */
  public int length() {
    return(end-start);
  }
  
  /**
   * Returns true is the specified span is contained by this span.  
   * Identical spans are considered to contain each other. 
   * @param s The span to compare with this span.
   * @return true is the specified span is contained by this span; false otherwise. 
   */
  public boolean contains(Span s) {
    return(start <= s.getStart() && s.getEnd() <= end);
  }
  
  /**
   * Returns true if the specified span intersects with this span.
   * @param s The span to compare with this span. 
   * @return true is the spans overlap; false otherwise. 
   */
  public boolean intersects(Span s) {
    int sstart = s.getStart();
    //either s's start is in this or this' start is in s
    return(this.contains(s) || s.contains(this) || 
	   (getStart() <= sstart && sstart < getEnd() ||
	   sstart <= getStart() && getStart() < s.getEnd()));
  }
  
  /**
   * Returns true is the specified span crosses this span.
   * @param s The span to compare with this span.
   * @return true is the specified span overlaps this span and contains a non-overlapping section; false otherwise.
   */
  public boolean crosses(Span s) {
    int sstart = s.getStart();
    //either s's start is in this or this' start is in s
    return(!this.contains(s) && !s.contains(this) && 
	   (getStart() <= sstart && sstart < getEnd() ||
	   sstart <= getStart() && getStart() < s.getEnd()));
  }
  
  public int compareTo(Object o) { 
    Span s = (Span) o;
    if (getStart() < s.getStart()) {
      return(-1);
    }
    else if (getStart() == s.getStart()) {
      if (getEnd() > s.getEnd()) {
        return(-1);
      }
      else if (getEnd() < s.getEnd()) {
        return(1);
      }
      else {
        return(0);
      }
    }
    else {
      return(1);
    }
  }

  public int hashCode() {
    return((this.start << 16) | (0x0000FFFF | this.end));
  }
  
  public boolean equals(Object o) {
    if (o == null) {
      return(false);
    }
    Span s = (Span) o;
    return(getStart() == s.getStart() && getEnd() == s.getEnd());
  }
  
  public String toString() {
    StringBuffer toStringBuffer = new StringBuffer(15);
    return(toStringBuffer.append(getStart()).append("..").append(getEnd()).toString());
  }

}
