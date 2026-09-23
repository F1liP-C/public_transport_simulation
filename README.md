# Public Transport Simulation

An object-oriented Java project for simulating simplified tram traffic in a city. The project was created for an Object-Oriented Programming course and uses an event-driven simulation model.

## Problem

In the simulated city, passengers live near stops and trams operate on routes assigned to specific lines. Each tram has a fleet number, a capacity and a direction of travel. A route runs from one terminus to the other, after which the tram returns along the same route. At both termini, it waits for a specified amount of time.

Every day:

- trams start operating at 6:00; half of them leave from one end of the route and the other half from the opposite end,
- the interval between trams is calculated from the full round-trip time and the number of trams on the line,
- passengers arrive at their stops at random times between 6:00 and 12:00,
- each passenger waits for a tram, randomly selects a destination further along the route and tries to reach it,
- when a tram arrives, passengers leave first and waiting passengers board afterwards,
- after 23:00, trams do not start new passenger journeys but finish journeys that have already started.

Stops and trams have limited capacity. If there is no space at a stop or in a tram, a passenger waits for another opportunity. At the end of the day, all passengers return home and the process starts again the next day.

## Project Goal

The main goal is to create an object-oriented model that can be extended with new types of vehicles, events and public transport components. The simulation is not controlled by real time. Every action is represented as an event with a scheduled time and processed in chronological order.

The program prints:

1. the input parameters,
2. a detailed log of arrivals, departures, boarding and leaving trams,
3. overall statistics and statistics for each day, including the number of passenger journeys and waiting time at stops.

## Running the Project

JDK 8 or newer is required.

Run the following commands from the project root:

```bash
javac -encoding UTF-8 -d out main/Main.java kolejka/*.java komunikacja/*.java main/*.java
java -ea -cp out main.Main < examples/example-input.txt
```

Input is read from standard input using `java.util.Scanner`, and results are printed to standard output.

## Input Format

The program reads the following values in order:

```text
number of simulation days
stop capacity
number of stops
stop name 1
...
stop name N
number of passengers
tram capacity
number of tram lines
```

For each line, the following values are provided:

```text
number of trams route length
stop name 1 travel time
...
stop name N travel time or waiting time
```

The last time in a route description is the waiting time at a terminus. The same waiting time applies at both ends of the route. Stop names cannot contain whitespace.

The example file [examples/example-input.txt](examples/example-input.txt) describes five days, five stops and two lines with partially shared routes.

## Architecture

The code is divided into three packages:

- **`kolejka`** - the `KolejkaZdarzen` interface, an array-based event queue and the event hierarchy: `Zdarzenie`, `ZdarzeniePasazer`, `ZdarzenieTramwaj`, `ZdarzenieTramwajPrzyjazd` and `ZdarzenieTramwajOdjazd`,
- **`komunikacja`** - the domain model: `Pasazer`, `Przystanek`, `Linia`, the abstract `Pojazd` and `Tramwaj`,
- **`main`** - the `Main` entry point, the `Symulacja` simulation controller, time formatting in `Czas` and the only source of randomness in `Losowanie`.

`KolejkaTablicowa` stores events in a dynamically resized array. Events with the same time retain their insertion order. The queue can be replaced with another implementation through the `KolejkaZdarzen` interface without changing the rest of the simulation. The project uses arrays instead of standard library collections, as required by the assignment.

## Project Structure

```text
.
├── kolejka/       # event queue and event hierarchy
├── komunikacja/   # passengers, stops, lines and vehicles
├── main/          # entry point, simulation and utilities
├── examples/      # sample input data
└── README.md
```

## Implementation Constraints

- Each route must contain at least two stops and each line must have at least one tram; invalid input is rejected.
- Capacities and simulation counts must be non-negative. Passengers require at least one stop, and stop names must be unique.
- Travel times between stops must be positive, while the waiting time at a terminus cannot be negative. Unknown stop names are reported as invalid input.
- All random choices in the simulation are made exclusively through the `Losowanie` class, as required by the assignment.