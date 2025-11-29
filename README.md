# y-combinator

In a attempt to find beauty inside programming, this is a study case of the Y combinator in Clojure, FSharp and Java.

## What is the Y combinator?

Quoting from [Mike Vanier article](https://mvanier.livejournal.com/2897.html):
>The Y combinator is a higher-order function, and it takes a single argument, which is a function that isn't recursive and it returns a version of the function which is recursive.
>More generally, Y gives us a way to get recursion in a programming language that supports first-class functions but that doesn't have recursion built in to it.
>So what Y shows us is that such a language already allows us to define recursive functions, even though the language definition itself says nothing about recursion.
> This is a Beautiful Thing: it shows us that functional programming alone can allow us to do things that we would never expect to be able to do 

The Y combinator needs to satisfy the property `(Y f) = (f (Y f))`. In a lazy language, that can be as follows:
```scheme
(lambda (f)
  ((lambda (x) (f (x x)))
   (lambda (x) (f (x x)))))
```
FSharp, Clojure and Java are not lazy language, so the definition above will cause a `StackOverFlow error` in the three languages. To avoid that, the definition needs to wrapped inside a lambda:
```scheme
(lambda (f)
  ((lambda (x) (f (lambda (y) ((x x) y))))
   (lambda (x) (f (lambda (y) ((x x) y))))))
```

One thing to notice is that the lambda expression won't be evaluated further until it's applied to its argument and the lambda wrapper doesn't change the value of the thing it wraps, but it does delay its evaluation.

## Results

Following the article from Mike Vanier, it's was simple to translate from Scheme to Clojure, because of the Lisp dialect. Still, it's was not easy to understand.

The FSharp one was a bit trick, because of the `let rec` form. If you think about it, one should be able to do the Y combinator without let rec, but because of how FSharp works, `let rec` can be treated as a primitive of the language. But still it's possible to do the Y Combinator with types, but that is not so trivial to understand.

The Java one seems a mess, but it's was a work made be Ken Shirrif in 2009, docummented in this [article](https://www.righto.com/2009/03/y-combinator-in-arc-and-java.html). It's verbose and it's ugly and it's does not accepts any functions, only function with numbers. In today version of java, I think it's possible to make accepts any function, not only numeric ones because of java 8, but I did not tested it. The conclusion working with java is: Objects are a poor man's closures. Clojure and FSharp seems better to understand than the java one.
