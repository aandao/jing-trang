package com.thaiopensource.relaxng.nrl;

import com.thaiopensource.util.Equal;

import java.util.Vector;
import java.util.Enumeration;

class ModeUsage {
  private final Mode mode;
  private final Mode currentMode;
  private ContextMap modeMap;
  private int attributeProcessing = -1;

  ModeUsage(Mode mode, Mode currentMode) {
    this(mode, currentMode, null);
  }

  private ModeUsage(Mode mode, Mode currentMode, ContextMap modeMap) {
    this.mode = mode;
    this.currentMode = currentMode;
    this.modeMap = modeMap;
  }

  ModeUsage changeCurrentMode(Mode currentMode) {
    return new ModeUsage(mode, currentMode, modeMap);
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof ModeUsage))
      return false;
    ModeUsage other = (ModeUsage)obj;
    return this.mode == other.mode && this.currentMode == other.currentMode && Equal.equal(this.modeMap, other.modeMap);
  }

  private Mode resolve(Mode mode) {
    return mode == Mode.CURRENT ? currentMode : mode;
  }

  int getAttributeProcessing() {
    if (attributeProcessing == -1) {
      attributeProcessing = resolve(mode).getAttributeProcessing();
      if (modeMap != null) {
        for (Enumeration enum = modeMap.values();
             enum.hasMoreElements()
             && attributeProcessing != Mode.ATTRIBUTE_PROCESSING_FULL;)
          attributeProcessing = Math.max(resolve((Mode)enum.nextElement()).getAttributeProcessing(),
                                         attributeProcessing);
      }
    }
    return attributeProcessing;
  }

  boolean isContextDependent() {
    return modeMap != null;
  }

  Mode getMode(Vector context) {
    if (modeMap != null) {
      Mode m = (Mode)modeMap.get(context);
      if (m != null)
        return resolve(m);
    }
    return resolve(mode);
  }

  boolean addContext(boolean isRoot, Vector names, Mode mode) {
    if (modeMap == null)
      modeMap = new ContextMap();
    return modeMap.put(isRoot, names, mode);
  }
}