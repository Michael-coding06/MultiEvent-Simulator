# Discrete Event Simulator
A Java-based discrete event simulator that models a multi-queue-multi-server system, similar to queuing systems found in fast-food restaurants or service centers.

## 🎯 About The Project This simulator models a queuing system where: - Multiple servers process customers independently - Each server maintains its own queue - Customers arrive, wait, get served, or leave based on system capacity - The simulation tracks performance metrics in real-time 

### Event Flow The system processes five types of events: 
- **ARRIVE**: Customer enters the system 
- **SERVE**: Server begins serving a customer 
- **WAIT**: Customer joins a queue 
- **LEAVE**: Customer exits without service (system full) 
- **DONE**: Server completes service
<img width="1620" height="779" alt="image" src="https://github.com/user-attachments/assets/b9c17c1c-19f8-4385-8d2d-79f7124524ad" />


## 🚀 Getting Started

### Prerequisites 
- Java 11 or higher 
- JShell (included with JDK 9+)


Run a test case using the following command: 
```bash 
cat test_case/1.in | java Main
```
