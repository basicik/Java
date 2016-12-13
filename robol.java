import java.util.*;

interface Robol {
    void interpret();
}

class Bottle {
	NumberExp x, y, dl;
	Bottle(NumberExp x, NumberExp y, NumberExp dl) {
		this.x = x;
		this.y = y;
		this.dl = dl;
	}
}

class Program implements Robol {
    public Grid grid;
    public Robot robot;

    public Program(Grid grid, Robot robot)
    {
        this.grid = grid;
        this.robot = robot;     
    }
    
    public void interpret()
    {
        robot.interpret();
    }
    
    public static void main(String[] args) {

        System.out.println("Test program 3");
		Program p = testProgram3();
        p.interpret();

    } 

   	static Program testProgram3() {
        Robot robo = new Robot();
        robo.setStart(new Start(robo, 23, 6, 10));
        List<Bottle> b = new ArrayList<Bottle>();
        b.add(new Bottle(new NumberExp(23),new NumberExp(6),new NumberExp(150)));
        List<VarDecl> varDecls = new ArrayList<VarDecl>();
		VarDecl iDecl = new VarDecl("i", 5);
        varDecls.add(iDecl);
		VarDecl jDecl = new VarDecl("j", 3);
        varDecls.add(jDecl);
        robo.setVarDecls(varDecls);

	    Grid g = new Grid(64, 64, b);
        robo.setGrid(g);

        List<Statement> statements = new ArrayList<Statement>();
        statements.add(new Drink(robo));
        statements.add(new North(robo, new MultExp(new NumberExp(3), new IdentifierExp(robo, "i")))); // til (23,21)
        statements.add(new West(robo, new NumberExp(15))); // til (8,21)
        statements.add(new East(robo, new NumberExp(4))); // til (12, 21)

		List<Statement> whileBody = new ArrayList<Statement>();
		whileBody.add(new South(robo, new IdentifierExp(robo, "j")));
		whileBody.add(new Assignment(robo, "j", new MinusExp(new IdentifierExp(robo, "j"), new NumberExp(1))));
        While whileStatement = new While(robo, new GreaterThanExp(new IdentifierExp(robo, "j"), new NumberExp(0)), whileBody);

        statements.add(whileStatement);

        robo.setStatements(statements);

        Program p = new Program(g, robo);
		return p;
    }  

}

class Robot implements Robol {
    List<VarDecl> varDecls;
    Start start;
    List<Statement> statements;
    int x;
    int y;
    boolean stopRequested = false;
    Grid grid;
    int energy; 
        
    public void setStart(Start start) {
        this.start = start;
    }
    
    public void setGrid(Grid grid) {
        this.grid = grid;
    }
    
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
    
    public List<VarDecl> getVarDecls() {
        return varDecls;
    }
    
    public void setVarDecls(List<VarDecl> varDecls) {
        this.varDecls = varDecls;
    }
    
    public void setEnergy(int energy) {
    	this.energy = this.energy + energy;
    }
    
    public void interpret()
    {
        start.interpret();
        for(Statement stmnt : statements)
        {
            if(stopRequested) {
                break;
            }
            stmnt.interpret();
            if(energy <= 0) {
            	System.out.println("out of alchol!");
            	return;
            }
        }
        
        System.out.println("My position is " + x + ", " + y + ", energy: " + energy);
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        
        if(!grid.checkBounds(x, y))
            throw new RuntimeException("OH NO, I stepped outside the end of the world at " + x + ", " + y);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    
    public void requestStop()
    {
        stopRequested = true;
    }   
}


class VarDecl
{
    String name;
    int value;

    public VarDecl(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}

class Grid 
{
    int xBound;
    int yBound;
    List<Bottle> bottle;
    
    public Grid(int xBound, int yBound, List<Bottle> bottle) {
        this.xBound = xBound;
        this.yBound = yBound;
        this.bottle = bottle;
    }
    
    public boolean checkBounds(int x, int y)
    {
        if(x < 0 || y < 0)
            return false;
        
        if(x > xBound || y > yBound)
            return false;
            
        return true;
    }
    
    public Bottle isBottle(int x, int y) {
    	for(int i = 0; i<=bottle.size(); i++){
    		if(bottle.get(i).x.getValue() == x && bottle.get(i).y.getValue() == y) {
    			return bottle.get(i);
    		}
    	}
    	return null;
    	
    }
}

class Start implements Robol {
    int x; 
    int y;
    Robot robot;
    int energy;
    
    public Start(Robot robot, int x, int y, int energy)
    {
        this.robot = robot;
        this.x = x;
        this.y = y;
        this.energy = energy;
    }
    
    public void interpret()
    {
        robot.setPosition(x, y);
        robot.setEnergy(energy);
    }
}

abstract class Statement implements Robol {
    
    protected Robot robot;
    
    public Statement(Robot robot) 
    {
        this.robot = robot;
    }
    
    public abstract void interpret();
}

class Assignment extends Statement {
    
    String variableName;
    Expression exp;
    
    public Assignment(Robot robot, String variableName, Expression exp) {
        super(robot);
        
        this.variableName = variableName;
        this.exp = exp;
    }
    
    public void interpret() {
        List<VarDecl> decls = robot.getVarDecls();
        for(VarDecl d : decls) {
            if(d.name.equals(variableName)) {
                d.value = exp.getValue();
            }
        }
    }
    
}

class Drink extends Statement{
	
	Bottle b;
	Drink(Robot robot) {
		super(robot);
	}
	
	public void interpret() {
		b = robot.grid.isBottle(robot.x, robot.y);
		if(b == null) {System.out.println("No bottle"); }
		else{
		robot.energy = robot.energy + b.dl.getValue();
		}
	}
}

abstract class Expression { 
    
    abstract int getValue();
}

class NumberExp extends Expression {
    int number;
    
    public NumberExp(int number)
    {
        this.number = number;
    }
    
    int getValue() { return number; }
}

abstract class ArithmeticExp extends Expression {
    protected Expression left;
    protected Expression right; 
}

abstract class BoolExp extends Expression {
    protected Expression left;
    protected Expression right; 
    
    abstract boolean truthValue();  
    
    int getValue() {
        return truthValue() ? 1 : 0;
    }
}

class LessThanExp extends BoolExp {
    public LessThanExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    boolean truthValue() {
        return left.getValue() < right.getValue();
    }
}

class GreaterThanExp extends BoolExp {
    public GreaterThanExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    boolean truthValue() {
        return left.getValue() > right.getValue();
    }
}

class EqualsExp extends BoolExp {
    public EqualsExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    boolean truthValue() {
        return left.getValue() == right.getValue();
    }
}

class PlusExp extends ArithmeticExp {
        
    public PlusExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    int getValue() {
        return left.getValue() + right.getValue();
    }
}

class MinusExp extends ArithmeticExp {
    
    public MinusExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    int getValue() {
        return left.getValue() - right.getValue();
    }
}

class MultExp extends ArithmeticExp {
        
    public MultExp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    int getValue() {
        return left.getValue() * right.getValue();
    }
}

class IdentifierExp extends Expression {
    Robot robot;
    String name;
    
    public IdentifierExp(Robot robot, String name) {
        this.robot = robot;
        this.name = name;
    }
    
    int getValue() {
        List<VarDecl> decls = robot.getVarDecls();
        if(decls != null)
        {
            for(VarDecl d : decls) {
                if(d.name.equals(this.name)) {
                    return d.getValue();
                }
            }
        }
        
        throw new RuntimeException("OH NOES, invalid variable name");
    }
}

abstract class Move extends Statement {

    public enum Direction { NORTH, SOUTH, EAST, WEST }
    
    Direction direction;
    Expression expression;
    
    public Move(Robot robot, Expression expression, Direction direction)
    {
        super(robot);
        this.expression = expression;
        this.direction = direction;
    }   
}

class North extends Move {
    
    public North(Robot robot, Expression expression) {
        super(robot, expression, Direction.NORTH);
    }
    
    public void interpret()
    {
        robot.setPosition(robot.getX(), robot.getY()  + expression.getValue());
		System.out.println("My position is " + robot.getX() + ", " + robot.getY());
		robot.energy = robot.energy - expression.getValue();
    }   
}

class South extends Move {
    
    public South(Robot robot, Expression expression) {
        super(robot, expression, Direction.NORTH);
    }
    
    public void interpret()
    {
        robot.setPosition(robot.getX(), robot.getY() - expression.getValue());
		System.out.println("My position is " + robot.getX() + ", " + robot.getY());
		robot.energy = robot.energy - expression.getValue();
    
    }   
}

class East extends Move {
    
    public East(Robot robot, Expression expression) {
        super(robot, expression, Direction.NORTH);
    }
    
    public void interpret()
    {
        robot.setPosition(robot.getX()  + expression.getValue(), robot.getY());
		System.out.println("My position is " + robot.getX() + ", " + robot.getY());
		robot.energy = robot.energy - expression.getValue();

    }   
}

class West extends Move {
    
    public West(Robot robot, Expression expression) {
        super(robot, expression, Direction.NORTH);
    }
    
    public void interpret()
    {
        robot.setPosition(robot.getX()  - expression.getValue(), robot.getY());
		System.out.println("My position is " + robot.getX() + ", " + robot.getY());
		robot.energy = robot.energy - expression.getValue();

    }   
}

class Stop extends Statement {
    public Stop(Robot robot) {
        super(robot);
    }
        
    public void interpret()
    {
        this.robot.requestStop();
    }
}

class While extends Statement {
    BoolExp boolExp;
    List<Statement> statements;
    
    public While(Robot robot, BoolExp boolExp, List<Statement> statements) {
        super(robot);
        this.boolExp = boolExp;
        this.statements = statements;
    }
    
    public void interpret() {
        while(boolExp.truthValue()) {
            for(Statement stmnt : statements)
            {
                if(robot.stopRequested)
                    break;
                stmnt.interpret();
            }
        }
    }   
}
