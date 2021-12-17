using System;
using Utility.Authentication;
using Utility.Entities;

namespace LoginExercise.Entities
{
    public class User : Entity
    {
        public string Username { get; set; }
        public PasswordHash Password { get; set; }
        public byte[] Salt => Password.Salt;
        public byte[] Hash => Password.Hash;

        public User(string username, PasswordHash password)
            : this(default, default, default, username, password)
        { }

        public User(long id, DateTime dateCreated, DateTime dateModified, string username, PasswordHash password)
            : base(id, dateCreated, dateModified)
        {
            Username = username;
            Password = password;
        }
    }
}
