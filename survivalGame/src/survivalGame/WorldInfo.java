package survivalGame;

public class WorldInfo {

	public final TileChunk[] chunks; 
	public final int worldSize; 
	public final int chunkSize;
	public final int chunkAmount;
	public WorldInfo(TileChunk[] chunks, int worldSize, int chunkSize) {
		this.chunks = chunks;
		this.worldSize = worldSize;
		this.chunkSize = chunkSize;
		chunkAmount = worldSize / chunkSize;
	}
}
