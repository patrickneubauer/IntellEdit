package at.ac.tuwien.big.autoedit.transfer;

public interface TransferFunction<T> {
	
	public T forward(T from);
	
	public T back(T from);
	
	public default TransferFunction<T> inverse() {
		return new TransferFunction<T>() {

			@Override
			public T forward(T from) {
				//Das funktioniert und ruft tatsächlich die "obere" TransferFunction auf ...
				return TransferFunction.this.back(from);
			}

			@Override
			public T back(T to) {
				return TransferFunction.this.forward(to);
			}
			
		};
	}
	


}
