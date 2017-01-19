package fr.ensisa.hassenforder.network;

public interface Protocol {
    
    // Port integers
    public static final int CHATROOMS_CMD_PORT_ID 	= 9876;
    public static final int CHATROOMS_MSG_PORT_ID 	= 9877;

    // Command integers
    public static final int CONNECT 			= 00;
    public static final int CONNECT_OK 			= 01;
    public static final int CONNECT_KO 			= 02;
    public static final int DISCONNECT 			= 03;

    public static final int CREATE 				= 10;
    public static final int CREATE_OK 			= 11;
    public static final int CREATE_KO 			= 12;

    public static final int LOAD 				= 20;
    public static final int LOAD_OK 			= 21;
    public static final int LOAD_KO 			= 22;

    public static final int SUBSCRIBE 			= 30;
    public static final int SUBSCRIBE_OK 		= 31;
    public static final int SUBSCRIBE_KO 		= 32;
    public static final int UNSUBSCRIBE 		= 33;
    public static final int UNSUBSCRIBE_OK 		= 34;
    public static final int UNSUBSCRIBE_KO 		= 35;

    public static final int NEW_MESSAGE 		= 40;
    public static final int NEW_MESSAGE_KO 		= 41;

    public static final int VALIDATE 			= 50;
    public static final int VALIDATE_KO 		= 51;
    public static final int INVALIDATE 			= 52;
    public static final int INVALIDATE_OK 		= 53;
    public static final int INVALIDATE_KO 		= 54;
    
    public static final int PENDING_MESSAGE		= 60;
    
    // Channel type integers
    public static final int FREE		      	= 100;
    public static final int MODERATED			= 101;

}
