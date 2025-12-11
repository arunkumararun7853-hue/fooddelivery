package com.tap.dao;

import java.util.List;

import com.tap.model.Menu;

public interface MenuDAO {
	Menu getMenu(int menuId);
	void updateMenu(Menu menu);
	void deleteMenu(int MenuId);
	List<Menu> getAlMenusByRestaurent(int restaurantId);
	void addMenu(Menu menu);
}
