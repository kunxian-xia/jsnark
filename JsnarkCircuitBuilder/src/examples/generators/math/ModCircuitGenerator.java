package examples.generators.math;

import circuit.eval.CircuitEvaluator;
import circuit.structure.CircuitGenerator;
import circuit.structure.Wire;
import examples.gadgets.math.ModGadget;

// VC{(a, b, r) : r = a % b \wedge 0 <= a < 2^bitLength \wedge 0 <= b < 2^bitLength}
//
// PoK{q: q*b + r = a \wedge 0 <= a < 2^bitLength \wedge 0 <= b < 2^bitLength}
//
// a, b are public input
// r    is  public output
// q    is  nizk input
//
public class ModCircuitGenerator extends CircuitGenerator {
    private ModGadget gadget;
    private Wire a;
    private Wire b;
    private int bitLength;

    public ModCircuitGenerator(String circuitName, int bitLength) {
        super(circuitName);
        this.bitLength = bitLength;
    }

    @Override
    protected void buildCircuit() {
        a = createInputWire("a");
        b = createInputWire("b");

        gadget = new ModGadget(a, b, bitLength, "mod gadget");
        Wire[] outputs = gadget.getOutputWires();
        assert outputs.length == 1;

        makeOutput(outputs[0], "r");
    }

    @Override
    public void generateSampleInput(CircuitEvaluator evaluator) {
        evaluator.setWireValue(a, 3728985);
        evaluator.setWireValue(b, 1023);
    }

    public static void main(String[] args) throws Exception {
        ModCircuitGenerator generator = new ModCircuitGenerator("mod_circuit", 96);

        generator.generateCircuit();
        generator.evalCircuit();
        generator.prepFiles();
        //generator.runLibsnark();
    }
}
