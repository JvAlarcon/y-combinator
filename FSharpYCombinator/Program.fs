open System

// The fixed point combinator can be written in two ways:
// Using let rec:
let rec fix f =
    let z x = (f (fix f)) x
    z

// Or declaring a recursive type:
type 'a Rec = Rec of ('a Rec -> 'a)
let y f =
    let f' (Rec x as rx) = f (fun y -> x rx y)
    f' (Rec f')

let almostFactorial (factorialWeak : int -> int) : (int -> int) =
    fun n ->
        if n = 0 then 1
        else n * factorialWeak (n - 1)

// It's better to use let rec because of the f# type system, so you can treat let rec as primitive of the language

let factorial = fix almostFactorial
let factorialTyped = y almostFactorial

[<EntryPoint>]
let main argv =
    let fact5 = factorial 5
    let fact8 = factorial 8
    printfn "Factorial with let rec: %A" fact5
    printfn "Facotiral with recursive type: %A" fact8
    0
