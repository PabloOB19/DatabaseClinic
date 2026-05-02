package Jpa;
//Siempre poner get transaction.commit() al final cuando cambiamos cosas, como con preparestatement en jdbc
//sin quiero leer uso query como statement 
//fichero persistance, cmabio ruta base de datos, nombre, ruta de db 
//en user y role :meter @entity, ,table, @id, @createtable 
// user y role pollos son clases narmales pera añadiendo pto anterior
//cre entitymanager 
//XML:crear carpeta vacía de xml
//anotaciones de xml en los pollos para pasarlos a xml 
//crear manager de XML 
//Marshall transforma lo de java a xml 

import javax.management.relation.Role;

/*
public class user implements UserManager{
	EntityManager sm;
	em = Persistance.createdEntity("nombre de XML ")
			si los roles están vacios, creamos roles 
			if(this.getaraoles.isEmpty()) {
				Role hsopital.hsopital..
				this.createtable(hospital)
			}
	
em.close();

}
createRole(Role role){
	em.detTransaction
	em.persist(role)
	em.getTransaction.commit()
}

assignrole()

public User getuser() {
	Query q = em.createNativeQuery("query")

	q.setParameter(name);
}
public User login(name.password) {

	Query q = em.createNativeQuery("query")
   q.setParameter(name);
	

}

<<<<<<< HEAD

=======
>>>>>>> branch 'main' of https://github.com/PabloOB19/DatabaseClinic.git

*/