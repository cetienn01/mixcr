/*
 * Copyright (c) 2014-2015, Bolotin Dmitry, Chudakov Dmitry, Shugay Mikhail
 * (here and after addressed as Inventors)
 * All Rights Reserved
 *
 * Permission to use, copy, modify and distribute any part of this program for
 * educational, research and non-profit purposes, by non-profit institutions
 * only, without fee, and without a written agreement is hereby granted,
 * provided that the above copyright notice, this paragraph and the following
 * three paragraphs appear in all copies.
 *
 * Those desiring to incorporate this work into commercial products or use for
 * commercial purposes should contact the Inventors using one of the following
 * email addresses: chudakovdm@mail.ru, chudakovdm@gmail.com
 *
 * IN NO EVENT SHALL THE INVENTORS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE, EVEN IF THE INVENTORS HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * THE SOFTWARE PROVIDED HEREIN IS ON AN "AS IS" BASIS, AND THE INVENTORS HAS
 * NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS. THE INVENTORS MAKES NO REPRESENTATIONS AND EXTENDS NO
 * WARRANTIES OF ANY KIND, EITHER IMPLIED OR EXPRESS, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A
 * PARTICULAR PURPOSE, OR THAT THE USE OF THE SOFTWARE WILL NOT INFRINGE ANY
 * PATENT, TRADEMARK OR OTHER RIGHTS.
 */
package com.milaboratory.mixcr.assembler;

public final class AssemblerEvent implements Comparable<AssemblerEvent> {
    //auxiliary status codes used instead of cloneIndex
    public static final int DROPPED = -2, DEFERRED = -3, EOF = -1;
    public final long alignmentsIndex;
    public final long readId;
    public final int cloneIndex;

    public AssemblerEvent(long alignmentsIndex, long readId, int cloneIndex) {
        if (cloneIndex == EOF)
            throw new IllegalArgumentException();
        this.alignmentsIndex = alignmentsIndex;
        this.readId = readId;
        this.cloneIndex = cloneIndex;
    }

    @Override
    public int compareTo(AssemblerEvent o) {
        return Long.compare(alignmentsIndex, o.alignmentsIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssemblerEvent)) return false;

        AssemblerEvent that = (AssemblerEvent) o;

        if (alignmentsIndex != that.alignmentsIndex) return false;
        if (cloneIndex != that.cloneIndex) return false;
        if (readId != that.readId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (alignmentsIndex ^ (alignmentsIndex >>> 32));
        result = 31 * result + (int) (readId ^ (readId >>> 32));
        result = 31 * result + cloneIndex;
        return result;
    }
}
