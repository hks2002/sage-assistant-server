/**********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                             *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                           *
 * @CreatedDate           : 2022-03-26 17:01:00                                                                       *
 * @LastEditDate          : 2025-07-23 21:10:25                                                                       *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                             *
 *********************************************************************************************************************/

package com.da.sage.assistant.model;

import com.alibaba.fastjson2.annotation.JSONType;

import lombok.Data;

@Data
@JSONType(alphabetic = false)
public class PnIndustrialization {

  private Integer checkDrawing;
  private String typo;
  private Integer complex;
  private Integer warnManufTool;
  private Integer warnCE;
  private Integer warnExtraSize;
  private Integer warnCasters;
  private Integer warnLifting;
  private Integer isComponents;
  private Integer rawAluminum;
  private Integer rawSteel;
  private Integer rawPlastic;
  private Integer rawVariousMaterial;
  private Integer rawSpecific;
  private Integer heatTreatment;
  private Integer machineGrinding;
  private Integer machineGear;
  private Integer ttsStand;
  private Integer ttsSpecific;
  private Integer ttsPainting;
  private Integer ttsPlastic;
  private Integer specialPackingBox;
  private Integer testLoad;
  private Integer testHydraulic;
  private Integer testBalancing;
  private Integer testVarious;
  private Integer controlCalibration;
  private Integer controlSpecific;
  private Integer control3D;

}
