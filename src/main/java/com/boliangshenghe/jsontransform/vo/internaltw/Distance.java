/**
  * Copyright 2017 bejson.com 
  */
package com.boliangshenghe.jsontransform.vo.internaltw;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2017-11-24 15:42:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Distance {
	@JsonProperty
	public int DistanceToProvince;
	@JsonProperty
	public int DistanceToCounty;
    public void setDistanceToProvince(int DistanceToProvince) {
         this.DistanceToProvince = DistanceToProvince;
     }
     public int getDistanceToProvince() {
         return DistanceToProvince;
     }

    public void setDistanceToCounty(int DistanceToCounty) {
         this.DistanceToCounty = DistanceToCounty;
     }
     public int getDistanceToCounty() {
         return DistanceToCounty;
     }

}