//Author: Michael Roshin
using System;
using System.Collections.Generic;
using System.Text;

namespace ShoppingApp.Entities
{
    public abstract class Entity
    {
        public long ItemId { get; set; }
        public DateTime DateCreated { get; set; }
        

        public Entity ()
        { }

        public Entity (long itemId, DateTime dateCreated)
        {
            ItemId = itemId;
            DateCreated = dateCreated;
        }

        public override bool Equals(object other)
        {
            if (!(other is Entity otherEntity))
                return false;

            if (ReferenceEquals(this, otherEntity))
                return true;

            if (GetType() != otherEntity.GetType())
                return false;

            return ItemId == otherEntity.ItemId;
        }

        public override int GetHashCode()
        {
            return (GetType().ToString() + ItemId).GetHashCode();
        }

        public override string ToString()
        {
            return $"{GetType().Name} entity {ItemId}";
        }

        public static bool operator ==(Entity a, Entity b)
        {
            if (a is null && b is null)
                return true;

            if (a is null || b is null)
                return false;

            return a.Equals(b);
        }

        public static bool operator !=(Entity a, Entity b)
        {
            return !(a == b);
        }
    }
}
