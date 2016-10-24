package at.ac.tuwien.big.autoedit.change.basic;

import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.Parameter;
import at.ac.tuwien.big.autoedit.change.parameter.SimpleParameter;
import at.ac.tuwien.big.autoedit.scope.ValueScope;

public class AddConstantChangeType extends AbstractFeatureChangeType<AddConstantChangeType, AddConstantChange>
	implements FeatureChangeType<AddConstantChangeType, AddConstantChange> {

	/**Da habe ich ein Scoping-Problem ...*/
	
	/**Wenn ich weiß, welchen Wert die ersten beiden Parameter haben, dann kann ich den dritten hier direkt setzen
	 * ohne dass er dynamisch auf die ersten beiden eingehen muss
	 */
	public AddConstantChangeType(Resource res, ValueScope<, ?> valueScope) {
		/**Wenn feature gesetzt, dann alle Objekte, die die entsprechende Klasse haben*/
		super(res,/**Wenn Objekt + Feature gesetzt, dann aktuelle größe der Collection*/
				SimpleParameter.fromType(pt, "index", 2),
				SimpleParameter.fromType(pt, "value", 3)
		);
	}

}
