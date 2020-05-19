package diet;

import java.util.ArrayList;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
    

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	private String name;
	private Food F;
	private double calories = 0.0;
	private double proteins = 0.0;
	private double carbs = 0.0;
	private double fat = 0.0;
	private double quantityTot = 0.0;
	
	private class Ingrediente{
		private String name;
		private double quantity;
		public Ingrediente(String name, double quantity)
		{
			this.name = name;
			this.quantity = quantity;
			
		}
	}
	public Recipe(String name, Food F)
	{
		this.name = name;
		this.F = F;
	}
	
	ArrayList <Ingrediente> ricetta = new ArrayList<>();
	public Recipe addIngredient(String material, double quantity) {
		this.ricetta.add(new Ingrediente(material, quantity));
		this.quantityTot += quantity;
		this.calories += (this.F.getRawMaterial(material).getCalories()*quantity)/100.0;
		this.carbs += (this.F.getRawMaterial(material).getCarbs()*quantity)/100.0;
		this.proteins += (this.F.getRawMaterial(material).getProteins()*quantity)/100.0;
		this.fat += (this.F.getRawMaterial(material).getFat()*quantity)/100.0;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getCalories() {
		return (this.calories*100.0)/this.quantityTot;
	}

	@Override
	public double getProteins() {
		return (this.proteins*100.0)/this.quantityTot;
	}

	@Override
	public double getCarbs() {
		return (this.carbs*100.0)/this.quantityTot;
	}

	@Override
	public double getFat() {
		return (this.fat*100.0)/this.quantityTot;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		double quantityFor100;
		for(Ingrediente i : this.ricetta)
		{
			quantityFor100 = (100.0 * i.quantity)/this.quantityTot;
			s.append(i.name + ": ").append(quantityFor100).append("\n");
		}
		return s.toString();
	}
}
