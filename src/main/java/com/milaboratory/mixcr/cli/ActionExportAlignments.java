/*
 * Copyright (c) 2014-2018, Bolotin Dmitry, Chudakov Dmitry, Shugay Mikhail
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
 * commercial purposes should contact the Inventors using the following email
 * addresses: licensing@milaboratory.com
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
package com.milaboratory.mixcr.cli;

import cc.redberry.pipe.OutputPort;
import cc.redberry.pipe.blocks.FilteringPort;
import com.milaboratory.mixcr.basictypes.VDJCAlignments;
import com.milaboratory.mixcr.basictypes.VDJCAlignmentsReader;
import com.milaboratory.mixcr.export.InfoWriter;
import com.milaboratory.util.SmartProgressReporter;
import io.repseq.core.VDJCLibraryRegistry;

import java.util.List;

public class ActionExportAlignments extends ActionExport<VDJCAlignments> {
    public ActionExportAlignments() {
        super(new ActionExportParameters<VDJCAlignments>(), VDJCAlignments.class);
    }

    @Override
    public void go0() throws Exception {
        try (VDJCAlignmentsReader reader = new VDJCAlignmentsReader(parameters.getInputFile(), VDJCLibraryRegistry.getDefault());
             InfoWriter<VDJCAlignments> writer = new InfoWriter<>(parameters.getOutputFile())) {
            SmartProgressReporter.startProgressReport("Exporting alignments", reader, System.err);
            writer.attachInfoProviders((List) parameters.exporters);
            writer.ensureHeader();
            VDJCAlignments alignments;
            long count = 0;
            long limit = parameters.getLimit();
            OutputPort<VDJCAlignments> alignmentsPort = new FilteringPort<>(reader, parameters.getFilter());
            while ((alignments = alignmentsPort.take()) != null && count < limit) {
                writer.put(alignments);
                ++count;
            }
        }
    }

    @Override
    public String command() {
        return "exportAlignments";
    }
}
