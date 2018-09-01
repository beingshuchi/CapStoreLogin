package com.cg.capstore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="image")
public class ImageBean {
	
	@Id
	@Column(name="image_id")
	private String imageId;
	@Column(name="image")
	private Byte[] image;
	@Column(name="image_name")
	private String imageName;
	@Column(name="image_type")
	private String imageType;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public Byte[] getImage() {
		return image;
	}
	public void setImage(Byte[] image) {
		this.image = image;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public ImageBean(String imageId, Byte[] image, String imageName, String imageType) {
		super();
		this.imageId = imageId;
		this.image = image;
		this.imageName = imageName;
		this.imageType = imageType;
	}
	public ImageBean() {
		super();
	}
	
}
