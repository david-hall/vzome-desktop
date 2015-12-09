package org.vorthmann.j3d;

import java.awt.event.MouseEvent;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


/**
 * Description here.
 * 
 * @author Scott Vorthmann 2003
 */
public abstract class Trackball extends MouseToolDefault {

    protected  int oldX = 0, oldY = 0;

    /**
     * Radians per pixel of drag.
     */
    protected double mSpeed;

    protected boolean mModal = true; // must drag mouse, false means just moving does it
    
	public Trackball( double speed, boolean modal )
	{
		init( speed, modal, 1 );
	}

	public Trackball()
    {
        this( 1.0, true );
    }
	

	public final void init( double speed, boolean modal )
	{
		mModal = modal;
		setSpeed( speed );
	}
	

	public final void init( double speed, boolean modal, int button )
	{
		mModal = modal;
		setSpeed( speed );
	}
	

    /**
     * Adjust the sensitivity of the trackball.
     * @param speed number of degrees per pixel of motion
     */
    public void setSpeed( double speed )
    {
        mSpeed = speed * Math .PI / (double) 180;
    }

    public void setModal( boolean value )
    {
        mModal = value;    
    }

    @Override
    public  void mousePressed( MouseEvent e )
    {
        oldX = e .getX();
        oldY = e .getY();
    }

    @Override
    public  void mouseDragged( MouseEvent e )
    {
        if ( mModal )
            trackballRolled( e );
    }

    @Override
    public  void mouseMoved( MouseEvent e )
    {
        if ( ! mModal )
            trackballRolled( e );
    }

    public enum SpinModeEnum {
        UNLOCKED,
        SPIN_ON_X,
        SPIN_ON_Y,
        LOCKED
    }
    protected static SpinModeEnum spinMode = SpinModeEnum.UNLOCKED;
    
    public final static void setSpinMode( SpinModeEnum mode ) {
        spinMode = mode; 
    }
    public final static SpinModeEnum getSpinMode( ) {
        return spinMode; 
    }

    private void trackballRolled( MouseEvent e )
    {
        // get the new coordinates
        int newX = e .getX();
        int newY = e .getY();
        // the angle in degrees is just the pixel differences
        int angleX = newX - oldX;
        int angleY = newY - oldY;
        // set the old values
        oldX = newX;
        oldY = newY;
        
        // apply spinMode
        switch (spinMode) {
            case SPIN_ON_X:
                angleX = 0;
                break;
            case SPIN_ON_Y:
                angleY = 0;
                break;
            case LOCKED:
                angleX = 0;
                angleY = 0;
                break;
            default:
                break;
        }

        double radians = ((double) angleY) * mSpeed;
        AxisAngle4d yAngle = new AxisAngle4d( new Vector3d( 1d, 0d, 0d ), radians );

        radians = ((double) angleX) * mSpeed;
        AxisAngle4d xAngle = new AxisAngle4d( new Vector3d( 0d, 1d, 0d ), radians );

		Matrix4d x = new Matrix4d();
        x.set( xAngle );
		Matrix4d y = new Matrix4d();
        y.set( yAngle );
        x .mul( y );
        Quat4d q = new Quat4d();
        x .get( q );

        trackballRolled( q );
    }
    
    /**
     * Subclasses can override and not call super, to own the events.
     * @param roll
     */
    protected abstract void trackballRolled( Quat4d roll );
}
