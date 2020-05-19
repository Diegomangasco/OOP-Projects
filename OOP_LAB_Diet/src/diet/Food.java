package diet;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {

	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	private ArrayList <rawMaterial> lista = new ArrayList<>();
	private ArrayList <Product> prodotti = new ArrayList<>();
	private ArrayList <Recipe> ricette = new ArrayList<>();
	private ArrayList <Menu> menu = new ArrayList<>();
	public void defineRawMaterial(String name,
									  double calories,
									  double proteins,
									  double carbs,
									  double fat){
		this.lista.add(new rawMaterial(name,calories, proteins,carbs,fat));
	}
	
	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> rawMaterials(){
		this.lista.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
		ArrayList <NutritionalElement> array= new ArrayList<>();
		int lung = this.lista.size();
		for(int i = 0; i<lung; i++)
		{
			array.add(this.lista.get(i));
		}
		return array;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	private rawMaterial getByName(String name)
	{
		for(rawMaterial r : this.lista)
		{
			if(r.getName().equals(name))
				return r;
		}
		return null;
	}
	public NutritionalElement getRawMaterial(String name){
		NutritionalElement N;
		if((N = getByName(name))!=null)
			return N;
		else
			System.out.println("Elemento non trovato");
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		this.prodotti.add(new Product(name, calories, proteins, carbs, fat));
	}
	
	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> products(){
		this.prodotti.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
		ArrayList <NutritionalElement> array= new ArrayList<>();
		int lung = this.prodotti.size();
		for(int i = 0; i<lung; i++)
			array.add(this.prodotti.get(i));
		return array;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	private Product getByNameP(String name)
	{
		for(Product p : this.prodotti)
		{
			if(p.getName().equals(name))
				return p;
		}
		return null;
	}
	public NutritionalElement getProduct(String name){
		NutritionalElement N;
		if((N = getByNameP(name))!=null)
			return N;
		else
			System.out.println("Elemento non trovato");
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe r = new Recipe(name, this);
		this.ricette.add(r);
		return r;
	}
	
	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<NutritionalElement> recipes(){
		this.ricette.sort((o1 , o2) -> o1.getName().compareTo(o2.getName()));
		ArrayList <NutritionalElement> array = new ArrayList<>();
		int lung = this.ricette.size();
		for(int i = 0; i<lung; i++)
			array.add(this.ricette.get(i));
		return array;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	private Recipe getByNameR(String name)
	{
		for(Recipe r : this.ricette)
		{
			if(r.getName().equals(name))
				return r;
		}
		return null;
	}
	public NutritionalElement getRecipe(String name){		
		NutritionalElement N;
		if((N = getByNameR(name))!=null)
			return N;
		else
			System.out.println("Elemento non trovato");
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = new Menu(name, this);
		this.menu.add(m);
		return m;
	}
	
}
