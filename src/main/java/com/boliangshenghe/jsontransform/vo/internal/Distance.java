/**
  * Copyright 2017 bejson.com 
  */
package com.boliangshenghe.jsontransform.vo.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2017-09-20 10:28:6
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Distance {
	public int nearestTownDistance;
    @JsonProperty
    public int DistanceToProvince;
    @JsonProperty
    public int DistanceToCounty;
    @JsonProperty
    public int DistanceToCity;
    
    public void setNearestTownDistance(int nearestTownDistance) {
         this.nearestTownDistance = nearestTownDistance;
     }
     public int getNearestTownDistance() {
         return nearestTownDistance;
     }
     @JsonIgnore
    public void setDistanceToProvince(int DistanceToProvince) {
         this.DistanceToProvince = DistanceToProvince;
     }
     @JsonIgnore
     public int getDistanceToProvince() {
         return DistanceToProvince;
     }

    public void setDistanceToCounty(int DistanceToCounty) {
         this.DistanceToCounty = DistanceToCounty;
     }
     public int getDistanceToCounty() {
         return DistanceToCounty;
     }

    public void setDistanceToCity(int DistanceToCity) {
         this.DistanceToCity = DistanceToCity;
     }
     public int getDistanceToCity() {
         return DistanceToCity;
     }

	
	
	/*private int nearestTownDistance;
	 @JsonProperty(value = "DistanceToProvince")  
    private int distanceToProvince;
	 @JsonProperty(value = "DistanceToCounty")  
    private int distanceToCounty;
	 @JsonProperty(value = "DistanceToCity")  
    private int distanceToCity;
    public void setNearestTownDistance(int nearestTownDistance) {
         this.nearestTownDistance = nearestTownDistance;
     }
     public int getNearestTownDistance() {
         return nearestTownDistance;
     }

    public void setDistanceToProvince(int distanceToProvince) {
         this.distanceToProvince = distanceToProvince;
     }
    @JSONField(name = "DistanceToProvince") 
     public int getDistanceToProvince() {
         return distanceToProvince;
     }

    public void setDistanceToCity(int distanceToCity) {
         this.distanceToCity = distanceToCity;
     }
    @JSONField(name = "DistanceToCity") 
     public int getDistanceToCity() {
         return distanceToCity;
     }
    
    @JSONField(name = "DistanceToCounty") 
	public int getDistanceToCounty() {
		return distanceToCounty;
	}
	public void setDistanceToCounty(int distanceToCounty) {
		this.distanceToCounty = distanceToCounty;
	}*/

}