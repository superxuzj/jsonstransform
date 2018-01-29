/**
  * Copyright 2017 bejson.com 
  */
package com.boliangshenghe.jsontransform.vo.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2017-09-20 10:28:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Stastics {

    private int townCount;
    @JsonProperty
    public int VillageCount;
    public void setTownCount(int townCount) {
         this.townCount = townCount;
     }
     public int getTownCount() {
         return townCount;
     }

    public void setVillageCount(int VillageCount) {
         this.VillageCount = VillageCount;
     }
     public int getVillageCount() {
         return VillageCount;
     }

}