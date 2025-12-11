package com.tap.model;

public class Menu {
	private int menuId;
    private String menuName;
    private Double price;
    private boolean isAvailable;
    private String description;
    private String imageUrl;
    private int restaurantId;
 
    
    
    public Menu() {
    }
    




	public Menu(int menuId, String menuName, Double price, boolean isAvailable, String description, String imageUrl,
			int restaurantId) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
		this.isAvailable = isAvailable;
		this.description = description;
		this.imageUrl = imageUrl;
		this.restaurantId = restaurantId;
	}



	public Menu(int menuId, String menuName, Double price, boolean isAvailable, String description, String imageUrl
			) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
		this.isAvailable = isAvailable;
		this.description = description;
		this.imageUrl = imageUrl;
	
	}


	public int getMenuId() {
		return menuId;
	}


	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}


	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double d) {
		this.price = d;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}





	public void setAvaliable(boolean boolean1) {
		// TODO Auto-generated method stub
		
	}

	public int getRestaurantId() {
		return restaurantId;
	}



	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}



	public void setPrice(Double price) {
		this.price = price;
	}

    
}
	
