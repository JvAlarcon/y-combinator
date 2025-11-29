package ycomb;

public class YFib {
    
	// Objects are a poor man's closures
	
    // Integer function returning a Integer function
    // int -> int
    interface IntFunc {
        int apply(int n);
    }
    
    // Function on int function returning an int function
    // (int -> int) -> (int -> int)
    interface IntFuncToIntFunc {
        IntFunc apply(IntFunc f);
    }
    
    // High-order function returning an int function
    // F: F -> (int -> int)
    interface FuncToIntFunc {
        IntFunc apply(FuncToIntFunc x);
    }
    
    // Function from IntFuncToIntFunc to IntFunc
    // ((int -> int) -> (int -> int)) -> (int -> int)
    interface IntFuncToIntFuncToIntFunc {
        IntFunc apply(IntFuncToIntFunc r);
    }
    
    public static void main(String[] args) {
        System.out.println(
            //Y Combinator
            (new IntFuncToIntFuncToIntFunc() { public IntFunc apply(final IntFuncToIntFunc r) {
                return (new FuncToIntFunc() { public IntFunc apply(final FuncToIntFunc f) {
                    return f.apply(f); }})
                .apply(new FuncToIntFunc() { public IntFunc apply(final FuncToIntFunc f) {
                    return r.apply(new IntFunc() { public int apply(int x) {
                        return f.apply(f).apply(x);}});}});}}
            ).apply(
                // Recursive generator function
                new IntFuncToIntFunc() { public IntFunc apply(final IntFunc f) {
                    return new IntFunc() { public int apply(int n) {
                        if (n == 0) return 0;
                        else if (n == 1) return 1;
                        else return f.apply(n-1) + f.apply(n-2); }};}})
            .apply(
                // Argument
                Integer.parseInt(args[0])));
    }
}