using System;

namespace Utility.Monads
{
    /// <summary>
    /// A Result object represents the result of an operation that could succeed or fail.
    /// </summary>
    /// <remarks>
    /// Every result must either be:
    /// <list type="number">
    /// <item>Successful, with a null error message, or</item>
    /// <item>Not successful, with a non-null error message.</item>
    /// </list>
    /// Use the static methods Success and Error to create Result objects.
    /// </remarks>
    public class Result
    {
        public bool Successful { get; }
        public string ErrorMessage { get; }

        protected Result(bool successful, string errorMessage)
        {
            Successful = successful;
            ErrorMessage = errorMessage;
        }

        public override string ToString()
        {
            return (Successful) ? nameof(Successful) : $"Error: {ErrorMessage}";
        }

        /// <summary>
        /// Returns a Result object that represents a successful operation.
        /// </summary>
        /// <returns>A Result object where Successful is true and ErrorMessage is null.</returns>
        public static Result Success()
        {
            return new Result(successful: true, errorMessage: null);
        }

        /// <summary>
        /// Returns a Result object that represents an error and contains an error message.
        /// </summary>
        /// <param name="errorMessage">Error message must not be null.</param>
        /// <returns>A Result object where Successful is false and ErrorMessage is non-null.</returns>
        /// <exception cref="ArgumentNullException">Error message must not be null.</exception>
        public static Result Error(string errorMessage)
        {
            ValidateErrorMessage(errorMessage);
            return new Result(successful: false, errorMessage);
        }

        protected static void ValidateErrorMessage(string errorMessage)
        {
            if (errorMessage == null)
                throw new ArgumentNullException(nameof(errorMessage), "Error message must not be null.");
        }
    }

    /// <summary>
    /// A Result&lt;<typeparamref name="T"/>&gt; object represents the result of an operation
    /// that could either successfully return a <typeparamref name="T"/> or else fail to do so.
    /// </summary>
    /// <remarks>
    /// Every result must either be:
    /// <list type="number">
    /// <item>Successful, with a null error message, and with resulting data of type <typeparamref name="T"/>, or</item>
    /// <item>Not successful, with a non-null error message, and with no data (default/null data).</item>
    /// </list>
    /// Use the static methods Success and Error to create Result&lt;<typeparamref name="T"/>&gt; objects.
    /// </remarks>
    public class Result<T> : Result
    {
        public T Data { get; }

        protected Result(bool successful, string errorMessage, T data)
            : base(successful, errorMessage)
        {
            Data = data;
        }

        public override string ToString()
        {
            return (Successful) ? $"{nameof(Successful)}: {Data}" : $"Error: {ErrorMessage}";
        }

        /// <summary>
        /// Returns a Result&lt;<typeparamref name="T"/>&gt; object
        /// that represents a successful operation with valid resulting data.
        /// </summary>
        /// <param name="data">The valid data to be returned by the successful operation.</param>
        /// <returns>A Result&lt;<typeparamref name="T"/>&gt; object where
        /// Successful is true, ErrorMessage is null,
        /// and Data stores the valid resulting data.</returns>
        public static Result<T> Success(T data)
        {
            return new Result<T>(successful: true, errorMessage: null, data);
        }

        /// <summary>
        /// Returns a Result&lt;<typeparamref name="T"/>&gt; object
        /// that represents an error and contains an error message.
        /// </summary>
        /// <param name="errorMessage">Error message must not be null.</param>
        /// <returns>A Result&lt;<typeparamref name="T"/>&gt; object where
        /// Successful is false, ErrorMessage is non-null, and Data is default/null.</returns>
        /// <exception cref="ArgumentNullException">Error message must not be null.</exception>
        public new static Result<T> Error(string errorMessage)
        {
            ValidateErrorMessage(errorMessage);
            return new Result<T>(successful: false, errorMessage, data: default);
        }
    }
}
