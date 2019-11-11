**usage:**


mvn clean package

java -jar target/matrix-1.0-SNAPSHOT-shaded.jar 666



**Results (Core i7 * 4):**



BitMatrix 2000*2000, sequential: 479 ms

BitMatrix 2000*2000, parallel:   209 ms

BitMatrix 10000*10000, sequential: 45335 ms

BitMatrix 10000*10000, parallel:   22795 ms

NumberMatrix\<Integer\> 2000*2000, sequential: 252460 ms
  
NumberMatrix\<Integer\> 2000*2000, parallel:   96163 ms
