/***********************************************************************************
 * Copyright (c) 2024 Alireza Khodakarami (Jiraiyah)                               *
 * ------------------------------------------------------------------------------- *
 * MIT License                                                                     *
 * =============================================================================== *
 * Permission is hereby granted, free of charge, to any person obtaining a copy    *
 * of this software and associated documentation files (the "Software"), to deal   *
 * in the Software without restriction, including without limitation the rights    *
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell       *
 * copies of the Software, and to permit persons to whom the Software is           *
 * furnished to do so, subject to the following conditions:                        *
 * ------------------------------------------------------------------------------- *
 * The above copyright notice and this permission notice shall be included in all  *
 * copies or substantial portions of the Software.                                 *
 * ------------------------------------------------------------------------------- *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR      *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,        *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE     *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER          *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,   *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE   *
 * SOFTWARE.                                                                       *
 ***********************************************************************************/

package jiraiyah.jinventory;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

/**
 * A specialized inventory class that extends {@link SimpleInventory} and implements {@link RecipeInput}.
 * This class is designed to handle inventory operations specifically for recipes.
 * It provides constructors to initialize the inventory with a specified size or with an array of {@link ItemStack} items.
 *
 * <p>Usage example:</p>
 * <pre>
 * {@code
 *     RecipeSimpleInventory inventory = new RecipeSimpleInventory(5);
 *     // or
 *     RecipeSimpleInventory inventory = new RecipeSimpleInventory(itemStack1, itemStack2);
 * }
 * </pre>
 *
 * <p>This class also overrides the {@code getStackInSlot} method to retrieve an {@link ItemStack} from a specific slot.</p>
 *
 * @see SimpleInventory
 * @see RecipeInput
 * @see ItemStack
 *
 * @author TurtyWurty
 */
public class RecipeSimpleInventory extends SimpleInventory implements RecipeInput
{
    /**
     * Constructs a new {@code RecipeSimpleInventory} with the specified size.
     * This constructor initializes the inventory with empty slots based on the given size.
     *
     * @param size the number of slots in the inventory. Must be a non-negative integer.
     * @throws IllegalArgumentException if the size is negative.
     */
    public RecipeSimpleInventory(int size)
    {
        super(size);
    }

    /**
     * Constructs a new {@code RecipeSimpleInventory} initialized with the specified {@link ItemStack} items.
     * This constructor allows for the creation of an inventory with predefined items.
     *
     * @param items an array of {@link ItemStack} objects to populate the inventory.
     *              Each element represents an item in a slot. The array can be empty.
     * @throws NullPointerException if the {@code items} array or any of its elements are null.
     */
    public RecipeSimpleInventory(ItemStack... items)
    {
        super(items);
    }

    /**
     * Retrieves the {@link ItemStack} from the specified slot in the inventory.
     * This method provides access to the item stored in a particular slot.
     *
     * @param slot the index of the slot from which to retrieve the {@link ItemStack}.
     *             Must be within the valid range of slot indices.
     * @return the {@link ItemStack} located in the specified slot.
     *         Returns an empty {@link ItemStack} if the slot is empty or the index is out of range.
     * @throws IndexOutOfBoundsException if the slot index is negative or exceeds the inventory size.
     */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return getStack(slot);
    }
}