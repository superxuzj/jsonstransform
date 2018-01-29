/**
  * Copyright 2017 bejson.com 
  */
package com.boliangshenghe.jsontransform.vo.overseas;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2017-09-20 10:21:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Distance {
	@JsonProperty
    public int DistanceToCapital;
	@JsonProperty
    public int DistanceToStateCapital;
    public void setDistanceToCapital(int DistanceToCapital) {
         this.DistanceToCapital = DistanceToCapital;
     }
     public int getDistanceToCapital() {
         return DistanceToCapital;
     }

    public void setDistanceToStateCapital(int DistanceToStateCapital) {
         this.DistanceToStateCapital = DistanceToStateCapital;
     }
     public int getDistanceToStateCapital() {
         return DistanceToStateCapital;
     }

}