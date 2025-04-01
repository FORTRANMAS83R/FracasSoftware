# SIT213 Project

![Main Pipeline Status](https://gitlab-df.imt-atlantique.fr/m23franc/fracassoftware/badges/main/pipeline.svg)
![Main Coverage](https://gitlab-df.imt-atlantique.fr/m23franc/fracassoftware/badges/main/coverage.svg)

![Develop Pipeline Status](https://gitlab-df.imt-atlantique.fr/m23franc/fracassoftware/badges/develop/pipeline.svg)
![Develop Coverage](https://gitlab-df.imt-atlantique.fr/m23franc/fracassoftware/badges/develop/coverage.svg)

## Description

SIT213 is a software project developed as part of an academic course. Students have to develop a simulation software of transmission of numerical signals.

## Documentation

The full documentation, including the Javadoc, is available at the following link:

- [Javadoc Documentation](https://fracassoftware-m23franc-4b6241379740f8ec8a3525b7a50c9d1b927f2fb.gitlab-df-pages.imt-atlantique.fr/)

## Features

- Feature 1: Simulate a transmission line w/ multiple parameters such as multipath, noise level...
- Feature 2: Calculate BER
- Feature 3: Display revelant diagrams such as eye diagram, noise distribution...

## Installation

To install and run the project locally, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://gitlab-df.imt-atlantique.fr/m23franc/fracassoftware.git

   ```

2. Navigate to the project directory:

```sh
cd fracassoftware
```

3. Compile the project

```sh
./compile
```

4. Run the simulator

```sh
./simulateur
```

## Parameters

### `-s`

Enables the use of display probes (affichage).

---

### `-seed <value>`

Sets the seed for random number generators.

- **Type**: Integer

---

### `-mess <value>`

Specifies the message to transmit.

- **Type**:
  - String of at least 7 binary digits (`[0,1]{7,}`) for a fixed message.
  - Integer (1 to 6 digits) for the number of bits in a random message.

---

### `-form <value>`

Specifies the waveform format.

- **Accepted Values**: `NRZ`, `NRZT`, `RZ`

---

### `-nbEch <value>`

Specifies the number of samples per symbol.

- **Type**: Integer (adjusted to the nearest multiple of 3 if not divisible by 3).

---

### `-ampl <min> <max>`

Specifies the minimum and maximum amplitude values.

- **Type**: Float (e.g., `-1.0`, `0.5`)

---

### `-snrpb <value>`

Specifies the signal-to-noise ratio per bit.

- **Type**: Float

---

### `-ti <dt1> <ar1> ... <dtN> <arN>`

Specifies multiple paths for multi-path transmission.

- **Type**:
  - `dt`: Integer (delay in time units, must be ≥ 0)
  - `ar`: Float (amplitude ratio, must be between 0 and 1)
- **Constraints**:
  - Maximum of 5 paths.
  - The sum of the squares of all amplitude ratios must be ≤ 1.

---

### `-codeur`

Enables the use of a coder.

- **Additional Constraints**:
  - For `RZ` waveform: `ampl_min` must be 0.
  - For `NRZ`/`NRZT` waveforms:
    - `ampl_max` must be ≥ 0.
    - `ampl_min` must be ≤ 0.
    - `ampl_max` must always be greater than `ampl_min`.

## Authors

This project was developed by:

Valentin COLOMER
Alexis COURBET
Mikael FRANCO
Maxence HUET
Jean TRONET

## License

This project is distributed under the MIT licence
