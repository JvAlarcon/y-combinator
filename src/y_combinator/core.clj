(ns y-combinator.core)

; The Y combinator is a higher-order function.
; It takes a single argument, which is a function that isn't recursive.
; It returns a version of the function which is recursive.

; More generally, Y gives us a way to get recursion in a programming language that supports first-class functions but that doesn't have recursion built in to it.
; So what Y shows us is that such a language already allows us to define recursive functions, even though the language definition itself says nothing about recursion.
; This is a Beautiful Thing: it shows us that functional programming alone can allow us to do things that we would never expect to be able to do 

; A "functional" is just a function that takes another function as input.
; The Y combinator finds the fixed point of the "functional" passed in as an argument
; It needs to satisfy the property: Y(F) = F(Y(F))
; Below the definition of the Y Combinator:
(def Y (fn [f] 
         (fn [x] f(fn [y] 
                    (x(x))(y)))
         (fn [x] f(fn [y] 
                    (x(x))(y)))))

; Note that the Y does not reference itself
(Y (println "hello"))
;=> hello
; #function[y-combinator.core/Y1/fn--10330]


; Let's make a factorial with Y combinator
; Remember, you cannot call the name of the function inside it. If you do, it's not a combinator
(def almost-factorial
  (fn [func]
    (fn [n]
      (if (= n 0)
        1
        (* n (func (dec n)))))))

; Clojure is not a lazy evaluation language, so to make Y combinator run, you need to wrap the (x x) inside a lambda, thus (fn [y] ((x x) y))
; That way, Clojure will understand that he doesn't need to evaluate (x x). The lambda expression won't be evaluated further until it's applied to its argument.
; This lambda wrapper doesn't change the value of the thing it wraps, but it does delay its evaluation
(defn Y [f]
  ((fn [x] (f (fn [y] ((x x) y))))
   (fn [x] (f (fn [y] ((x x) y))))))

(def factorial (Y almost-factorial))
(println (factorial 5))
(println (factorial 100)) ; Error - long overflow
(println (factorial 20))
(map factorial (range 20))

; Let's make a Fibonacci with Y combinator
(def almost-fibonacci
  (fn [func]
    (fn [n]
      (cond
        (= n 0) 0
        (= n 1) 1
        :else (+ (func (dec n)) (func (- n 2)))))))

(def fibonacci (Y almost-fibonacci))
(println (fibonacci 10))
(map fibonacci (range 20))
