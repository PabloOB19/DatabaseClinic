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
//XML
//crear carpeta para ficheros xml 
//rute element(el hospital) , elements, atributes, Type(orden en el que se ponen las cosas ) poner estos cosos con @xml 
// no hay métodos en xml, solo atributos
// elementos: cosas dentro de otras (hospital : dentro maquinas) y hay muchos  , hospital id : atributo
//@xmlElementWraper : "envoltorio de elementos"
////crear manager de XML 
//MANAGER
//import y un export 
//necesito un contexto para marshall ; en el context meto una instancia del pollo modificado con xml
//unmarshall le meto un file y me lo traduce a un objeto 
//Marshall transforma lo de java a xml, unmarshall al revés(converir todos los elementos), van en el manager

//import javax.management.relation.Role;

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