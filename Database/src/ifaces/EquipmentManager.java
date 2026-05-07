package ifaces;

import POJOS.*;
import java.util.List;

import Enums.Category;

public interface EquipmentManager {
	void insertEquipment(Equipment equipment);
	Equipment getEquipmentById(int id);
	List<Equipment> listAllEquipments();
	void updateEquipment(Equipment equipment);
	void deleteEquipment(int id);
	List<Equipment> listEquipmentBySurgery(int surgery_Id);
	List<Equipment> listEquipmentByCategory (Category category); 


}
