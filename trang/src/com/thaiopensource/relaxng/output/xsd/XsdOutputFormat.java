package com.thaiopensource.relaxng.output.xsd;

import com.thaiopensource.relaxng.output.OutputFormat;
import com.thaiopensource.relaxng.output.OutputDirectory;
import com.thaiopensource.relaxng.output.OutputFailedException;
import com.thaiopensource.relaxng.output.common.ErrorReporter;
import com.thaiopensource.relaxng.edit.SchemaCollection;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XsdOutputFormat implements OutputFormat {
  public void output(SchemaCollection sc, OutputDirectory od, ErrorHandler eh) throws SAXException, IOException, OutputFailedException {
    try {
      ErrorReporter er = new ErrorReporter(eh, XsdOutputFormat.class);
      if (er.getHadError())
        throw new OutputFailedException();
    }
    catch (ErrorReporter.WrappedSAXException e) {
      throw e.getException();
    }
  }
}
