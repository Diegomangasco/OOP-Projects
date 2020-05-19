package diet;

import java.util.ArrayList;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	private String name;
	private Food F;
	private double calories = 0.0;
	private double proteins = 0.0;
	private double carbs = 0.0;
	private double fat = 0.0;
	ArrayList <String> prodotti = new ArrayList<>();
	ArrayList <Ricetta> ricette = new ArrayList<>();
	public Menu(String name, Food F)
	{
		this.name = name;
		this.F = F;
	}
	private class Ricetta
	{
		private String name;
		private double quantity;
		public Ricetta(String name, double quantity)
		{
			this.name = name;
			this.quantity = quantity;
		}
	}
	public Menu addRecipe(String recipe, double quantity) {
		this.ricette.add(new Ricetta(recipe, quantity));
		this.calories += (quantity*this.F.getRecipe(recipe).getCalories())/100.0;
		this.carbs += (quantity*this.F.getRecipe(recipe).getCarbs())/100.0;
		this.proteins += (quantity*this.F.getRecipe(recipe).getProteins())/100.0;
		this.fat += (quantity*this.F.getRecipe(recipe).getFat())/100.0;
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		this.prodotti.add(product);
		this.calories += this.F.getProduct(product).getCalories();
		this.carbs += this.F.getProduct(product).getCarbs();
		this.proteins += this.F.getProduct(product).getProteins();
		this.fat += this.F.getProduct(product).getFat();
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return this.calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return this.proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return this.carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return this.fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
