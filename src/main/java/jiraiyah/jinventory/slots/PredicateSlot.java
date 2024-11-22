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

package jiraiyah.jinventory.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

/**
 * A custom slot implementation that allows for conditional item insertion based on a specified predicate.
 * This class extends the {@link Slot} class and provides additional functionality to control
 * which items can be inserted into the slot.
 *
 * <p>Two constructors are provided:
 * <ul>
 *   <li>One that accepts a custom predicate to determine if an item can be inserted.</li>
 *   <li>Another that defaults to using the {@link SimpleInventory#canInsert} method as the predicate.</li>
 * </ul>
 *
 * <p>Usage of this class allows for more flexible inventory management by enabling
 * custom rules for item insertion.
 *
 * <p>Example:
 * <pre>{@code
 *     PredicateSlot slot = new PredicateSlot(inventory, index, x, y, itemStack -> itemStack.isOf(Items.DIAMOND));
 * }</pre>
 * This example creates a slot that only accepts diamond items.
 *
 * @see Slot
 * @see Inventory
 * @see SimpleInventory
 * @see Predicate
 * @see ItemStack
 *
 * @version 1.0
 * @since 2023
 *
 * @author TurtyWurty
 */
@SuppressWarnings("unused")
public class PredicateSlot extends Slot
{
    /**
     * A predicate that determines whether an {@link ItemStack} can be inserted into this slot.
     *
     * <p>This predicate is used in the {@link #canInsert(ItemStack)} method to evaluate
     * if a given item stack is allowed to be placed in the slot. The predicate is defined
     * during the construction of the {@code PredicateSlot} and can be customized to enforce
     * specific rules for item insertion.
     *
     * <p>Example usage:
     * <pre>{@code
     *     Predicate<ItemStack> diamondOnly = itemStack -> itemStack.isOf(Items.DIAMOND);
     *     PredicateSlot slot = new PredicateSlot(inventory, index, x, y, diamondOnly);
     * }</pre>
     * This example creates a slot that only accepts diamond items.
     */
    private final Predicate<ItemStack> predicate;

    /**
     * Constructs a new {@code PredicateSlot} with a specified inventory, slot index, position, and item insertion predicate.
     *
     * <p>This constructor allows for the creation of a slot that can enforce custom rules for item insertion
     * based on the provided predicate. The predicate is used to determine whether an {@link ItemStack} can be
     * inserted into this slot.
     *
     * @param inventory The {@link Inventory} to which this slot belongs. It represents the container that holds the items.
     * @param index The index of the slot within the inventory. This determines the slot's position in the inventory array.
     * @param x The x-coordinate of the slot's position on the screen. This is used for rendering the slot in the GUI.
     * @param y The y-coordinate of the slot's position on the screen. This is used for rendering the slot in the GUI.
     * @param predicate A {@link Predicate} that tests whether an {@link ItemStack} can be inserted into this slot.
     *                  It allows for custom logic to be applied to item insertion.
     *
     * <p>Example usage:
     * <pre>{@code
     *     Predicate<ItemStack> diamondOnly = itemStack -> itemStack.isOf(Items.DIAMOND);
     *     PredicateSlot slot = new PredicateSlot(inventory, index, x, y, diamondOnly);
     * }</pre>
     * This example creates a slot that only accepts diamond items.
     */
    public PredicateSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> predicate)
    {
        super(inventory, index, x, y);
        this.predicate = predicate;
    }

    /**
     * Constructs a new {@code PredicateSlot} with a specified simple inventory, slot index, and position.
     *
     * <p>This constructor initializes a slot using a {@link SimpleInventory} and defaults to using the
     * {@link SimpleInventory#canInsert} method as the predicate for determining whether an {@link ItemStack}
     * can be inserted into this slot. This provides a straightforward way to create slots that adhere to
     * the default insertion rules of the {@code SimpleInventory}.
     *
     * @param inventory The {@link SimpleInventory} to which this slot belongs. It represents the container that holds the items.
     * @param index The index of the slot within the inventory. This determines the slot's position in the inventory array.
     * @param x The x-coordinate of the slot's position on the screen. This is used for rendering the slot in the GUI.
     * @param y The y-coordinate of the slot's position on the screen. This is used for rendering the slot in the GUI.
     *
     * <p>Example usage:
     * <pre>
     *     SimpleInventory inventory = new SimpleInventory(9);
     *          <p>
     *     PredicateSlot slot = new PredicateSlot(inventory, 0, 10, 10);
     * </pre>
     * This example creates a slot at index 0 with default insertion rules.
     */
    public PredicateSlot(SimpleInventory inventory, int index, int x, int y)
    {
        this(inventory, index, x, y, inventory::canInsert);
    }

    /**
     * Determines whether the specified {@link ItemStack} can be inserted into this slot.
     *
     * <p>This method uses the predicate defined for this {@code PredicateSlot} to evaluate
     * if the given item stack is allowed to be placed in the slot. The predicate provides
     * custom logic for item insertion, allowing for flexible inventory management.
     *
     * @param stack The {@link ItemStack} to be evaluated for insertion. This represents the item
     *              that is being checked against the slot's insertion rules.
     * @return {@code true} if the item stack can be inserted into the slot according to the predicate;
     *         {@code false} otherwise.
     *
     * <p>Example usage:
     * <pre>{@code
     *     ItemStack diamondStack = new ItemStack(Items.DIAMOND);
     *     boolean canInsertDiamond = slot.canInsert(diamondStack);
     * }</pre>
     * This example checks if a diamond item stack can be inserted into the slot.
     */
    @Override
    public boolean canInsert(ItemStack stack)
    {
        return this.predicate.test(stack);
    }
}