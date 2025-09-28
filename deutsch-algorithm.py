import pennylane as qml
import numpy as np

# Creates the quantum device
dev = qml.device('default.qubit', wires=2, shots=1)

# Function that will be applied
def f(x):
    # f(0) = 0 | f(1) = 0 -> constant function (always 0)
    if x == 0:
        return 0
    if x == 1:
        return 0

# Prepares the oracle
def U(g):
    u = np.array([
        [1 - g(0), g(0), 0, 0],
        [g(0), 1 - g(0), 0, 0],
        [0, 0, 1 - g(1), g(1)],
        [0, 0, g(1), 1 - g(1)]
    ])
    return u

# Defines the U_f matrix
U_f = U(f)

# Creates a QNode
@qml.qnode(dev)
def circuit():
    # Initializa the qubits
    qml.PauliX(wires=1)

    # Applies the Hadamards
    qml.Hadamard(wires=0)
    qml.Hadamard(wires=1)

    # Apply the oracle
    qml.QubitUnitary(U_f, wires=[0, 1])

    # Applies the final Hadamard
    qml.Hadamard(wires=0)

    # Measure
    return qml.sample(wires=0)

result = circuit()

if (result == 0):
    print(result, " constant function")

if (result == 1):
    print(result, " balanced function")