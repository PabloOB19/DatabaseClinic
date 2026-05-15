package ifaces;

import POJOS.*;
import java.util.List;

import Enums.Category;

public interface EquipmentManager {
	boolean insertEquipment(Equipment equipment);
	Equipment getEquipmentById(int id);
	List<Equipment> listAllEquipments();
	boolean updateEquipment(Equipment equipment);
	boolean deleteEquipment(int id);
	List<Equipment> listEquipmentBySurgery(int surgery_Id);
	List<Equipment> listEquipmentByCategory (Category category); 


}
