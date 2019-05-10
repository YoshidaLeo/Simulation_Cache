import java.lang.Integer;
import java.io.File;
import java.util.Scanner; 

public class Main {
	public static void main(String[] args) throws Exception{
		CPU test = new CPU();
		String[] instructions = new String[100];
		
		File file = new File("C:\\Users\\Leo PC\\Desktop\\Leo\\eclipse JAVA\\CSCI 113 proj\\src\\test.txt"); 
		Scanner sc = new Scanner(file); 
		
		// Read and Store file in array
		int i = 0;	  
		while (sc.hasNextLine()) {
			instructions[i] = (sc.nextLine());
			i++;
		}
		
		for(int j = 0; j < i; j++) {
			// Begin executing instructions
			int instr = Integer.parseInt(instructions[j].substring(0, 6),2);
			int addr = Integer.parseInt(instructions[j].substring(12, 18),2)/4;
			int offSet = Integer.parseInt(instructions[j].substring(19, 32),2);
			int wordAddr = (addr + offSet)/4;
			int cacheSet = wordAddr%8;
			int cacheTag = wordAddr/8;
			// Load
			if(instr == 35) {
				// READ HIT
				System.out.println("lw  " + addr + " " + offSet);
				if(test.cache[cacheSet][0].valid == 1 && test.cache[cacheSet][0].tag == cacheTag) {
					System.out.println("HIT");
					test.reg[addr] = test.cache[cacheSet][0].data;
					test.cache[cacheSet][0].hist = 1;
					test.cache[cacheSet][1].hist = 0;
				}
				else if(test.cache[cacheSet][1].valid == 1 && test.cache[cacheSet][1].tag == cacheTag) {
					System.out.println("HIT");
					test.reg[addr] = test.cache[cacheSet][1].data;
					test.cache[cacheSet][0].hist = 0;
					test.cache[cacheSet][1].hist = 1;
				}
				// READ MISS
				else {
					System.out.println("MISS");
					int victimBlock = 0;
					if(test.cache[cacheSet][0].hist == 0) {
						victimBlock = 0;
					}if(test.cache[cacheSet][1].hist == 0) {
						victimBlock = 1;
					}
					test.cache[cacheSet][victimBlock].tag = cacheTag;
					test.cache[cacheSet][victimBlock].valid = 1;
					test.cache[cacheSet][victimBlock].data = test.mem[wordAddr];
					test.reg[addr] = test.cache[cacheSet][victimBlock].data;
				}
			}
			// Store
			if(instr == 43) {
				System.out.println("sw  " + addr + " " + offSet);
				// Store HIT
				if(test.cache[cacheSet][0].valid == 1 && test.cache[cacheSet][0].tag == cacheTag) {
					System.out.println("HIT");
					test.cache[cacheSet][0].data = test.reg[addr];
				}
				else if(test.cache[cacheSet][1].valid == 1 && test.cache[cacheSet][1].tag == cacheTag) {
					System.out.println("HIT");
					test.cache[cacheSet][1].data = test.reg[addr];
				}
				// Store MISS
				else {
					System.out.println("MISS");
					test.mem[wordAddr] = test.reg[addr];
					
				}
			}
			System.out.println();						
		}
		test.printCPU();
	}
}
