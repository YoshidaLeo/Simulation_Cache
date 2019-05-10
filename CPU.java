public class CPU {
	
	public int[] mem = new int[128];
	public Cache[][] cache = new Cache[8][2];
	public int[] reg = new int[8];
	
	public CPU() {		
		for(int i=0; i < 128; i++) {
			mem[i] = i + 5;
		}
		
		for(int i=0; i < 8; i++) {
			reg[i] = 0;
		}
		
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				cache[i][j] = new Cache();
			}
		}		
	}
	public void printCPU() {
		System.out.println("Main Memory:");
		for(int i=0; i < 128; i++) {
			System.out.print(mem[i] + ", ");
		}
		System.out.println();
		System.out.println("Register::");
		for(int i=0; i < 8; i++) {
			System.out.print(reg[i] + ", ");
		}
		System.out.println();
		System.out.println("Cache (valid, tag, hist, data):");
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print("(" + cache[i][j].valid + ", " + cache[i][j].tag + ", " + cache[i][j].hist + ", " + cache[i][j].data + "), ");
			}
		}		
	}
}
