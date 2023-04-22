package tesisv1;
import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author miguel
 */
public class MusicPanel extends JPanel implements EstadoListener,KeyListener{

   // Piano piano;
    
    private final int staticModifier = 60;
         private int velocityAdd = 80; //original es 50
	private int pitchBend = 8192; //default centro de espectro
	private int longModifier = 0;
	private int instrument = 1;

	//Componentes MIDI
	Synthesizer synth = null;
	MidiChannel channels[];
	Instrument[] instrs;
        private boolean[] isActive = new boolean[123];

	//UI Components
	JLabel keyLabel,instrumentLabel;
    
     public MusicPanel(){
          
            try
		{
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch(Exception e) { e.printStackTrace();}

		channels = synth.getChannels();
		Soundbank bank = synth.getDefaultSoundbank();
		synth.loadAllInstruments(bank);
		instrs = synth.getLoadedInstruments();

		keyLabel = new JLabel();
		instrumentLabel = new JLabel();
		updateKey();
		updateInstrument();

		this.setLayout(new GridLayout(5,1));
                 this.setBackground(Color.white);
		this.add(keyLabel);
		this.add(instrumentLabel);
                 this.setPreferredSize(new Dimension(300, 200));
                 this.addKeyListener(this);  

        this.setFocusable(true); 
     }
     
     
     @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
	public void keyTyped(KeyEvent e){}
     
        public void keyPressed(KeyEvent e) {
		
			 switch (e.getKeyCode()) {
 				 case KeyEvent.VK_RIGHT: changeLongModifier(1) ; break;
				 case KeyEvent.VK_LEFT : changeLongModifier(-1) ; break; //aumentar escala
				 case KeyEvent.VK_UP   : changeVelocity(1);    break;  //disminuir volmen
				 case KeyEvent.VK_DOWN : changeVelocity(-1); break;
				 case KeyEvent.VK_EQUALS : pitchBend= Math.min(pitchBend+500,32692); break;
				 case KeyEvent.VK_MINUS :  pitchBend= Math.max(pitchBend-500,192); break;
				 case KeyEvent.VK_CLOSE_BRACKET : changeInstrument(1);	break;
				 case KeyEvent.VK_OPEN_BRACKET :  changeInstrument(-1); break;
			 }
			channels[1].setPitchBend(pitchBend);
			this.repaint();  // mostrar cambios
		
    }
        
           public void keyReleased(KeyEvent e)
	{
	  
	}
     public void triggerNote(Nota e, double velocidad, String action)
	{
            //int velocity = ajusteVelocidad(velocidad);
            int velocity = velocityAdd;
            
		int starter = e.numMIDI;
		if( (isActive[starter] && action.equals("start")) ||
				(! isActive[starter] && action.equals("kill"))) return;

		int myNoteValue = starter;

		if(action.equals("start"))
		{
                    if(AuxPrint.velocidadTecla)
                        System.out.println("  velY  " + velocity);
                    
		    isActive[starter] = true;
	            channels[1].noteOn(myNoteValue,velocity);     
		}
		else
		{
			isActive[starter] = false;
			channels[1].noteOff(myNoteValue);
		}
	}

      private int ajusteVelocidad(double velocidad) {
         int auxV = (int) (-velocidad);
         return auxV;
      }
      
	public void changeInstrument(int i)
	{
		if(instrument + i < instrs.length && instrument + i >= 0)
		{
			Patch instrumentPatch = instrs[instrument+i].getPatch();
			channels[1].programChange(instrumentPatch.getBank(),
                instrumentPatch.getProgram());
			instrument += i;
			updateInstrument();
		}
	}

	public void changeLongModifier(int amount)
	{
		if(isPlaying()) return;
		longModifier += ((longModifier+staticModifier)+amount >= 0 && (longModifier+staticModifier)+amount <= 127)? amount:0;
		updateKey();
	}


	public void changeVelocity(int amount)
	{
		velocityAdd += amount;
	}

	public boolean isPlaying()
	{
		for(int i = 0; i < isActive.length; i++) if(isActive[i]) return true;
		return false;
	}

	public void updateInstrument()
	{
		instrumentLabel.setText("Instrumento:  " + instrs[instrument].getName());

	}
	public void updateKey()
	{
	}

	public boolean isNoteKey(KeyEvent e)
	{
		int val = (int)e.getKeyChar();
		return ((val >= 65 && val <= 90) || (val >= 97 && val <= 122));
	}
     
     
     
    @Override
    public void estadoRecibido(EstadoEvento event) {
       if (event.estado() == Estado.PRESIONADO){
            if(AuxPrint.teclaPresSolt)
                System.out.println("Tecla presionada  " +event.nota().nombre);
      
            triggerNote(event.nota(), event.velocidadY(), "start");
        } else{
            if(AuxPrint.teclaPresSolt)
                System.out.println("Tecla Soltada  "+event.nota().nombre);
            
             triggerNote(event.nota(), event.velocidadY() , "kill");
        }
    
    }

   
    
}
