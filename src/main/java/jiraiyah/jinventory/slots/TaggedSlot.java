package jiraiyah.jinventory.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;

/**
 * A specialized slot implementation that extends {@link PredicateSlot} to create a slot that accepts
 * items with matching tags.
 *
 * <p>This class is designed to prevent any item from being inserted into the slot that doesn't
 * have a tag by using a predicate. This makes it suitable for use in scenarios where items should
 * only be extracted from the slot, such as crafting or processing outputs.
 *
 * <p>Example usage:
 * <pre>{@code
 *     Inventory inventory = new SimpleInventory(9);
 *     TaggedSlot taggedSlot = new TaggedSlot(inventory, 0, 10, 10, SOME_TAG_KEY);
 * }</pre>
 * This example creates a slot at index 0 that only accepts items that has been added to SOME_TAG_KEY.
 *
 * @see PredicateSlot
 * @see Inventory
 * @see Slot
 *
 * @version 1.0
 * @since 2023
 *
 * @author Jiraiyah
 */
@SuppressWarnings("unused")

public class TaggedSlot extends PredicateSlot
{
    /**
     * Constructs an {@code TaggedSlot} with the specified inventory, index, and position.
     *
     * <p>This constructor initializes a slot that only accepts items that are in the given tag key.
     * The slot is associated with the given inventory and positioned at the specified coordinates.
     *
     * @param inventory the inventory to which this slot belongs
     * @param index the index of the slot within the inventory
     * @param x the x-coordinate of the slot's position on the screen
     * @param y the y-coordinate of the slot's position on the screen
     * @param tagKey the item tag key to check the predicate for slot with
     */
    public TaggedSlot(Inventory inventory, int index, int x, int y, TagKey<Item> tagKey)
    {
        super(inventory, index, x, y, itemStack -> itemStack.isIn(tagKey));
    }
}