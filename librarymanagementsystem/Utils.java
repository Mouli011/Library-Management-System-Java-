{

    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final Console console = System.console();

    public static double getPositiveDoubleValue() {
        System.out.println("Enter Non Zero Value!!\n");
        double value = scanner.nextInt();
        return Utils.checkNGetPositiveDoubleValue(value);
    }

    public static double checkNGetPositiveDoubleValue(double value) {
        if (value <= 0) {
            value = Utils.getPositiveDoubleValue();
        }
        return value;
    }

    public static int getPositiveIntValue() {
        System.out.println("Enter Non Zero Value!!\n");
        int value = scanner.nextInt();
        return Utils.checkNGetPositiveIntValue(value);
    }

    public static int checkNGetPositiveIntValue(int value) {
        if (value <= 0) {
            value = Utils.getPositiveIntValue();
        }
        return value;
    }

    public static String getNotNull(String value) {

        try {
            System.out.println("This Field cannot be empty!!\nEnter Values: ");
            value = bufferedReader.readLine();

        } catch (IOException e) {
            System.out.println(e);
        }

        return Utils.checkNGetNotNull(value);
    }

    public static String checkNGetNotNull(String value) {
        if (value.length() == 0) {
            value = Utils.getNotNull(value);
        }
        return value;
    }

    public static String checkNGetValidPhoneNumber(String value) {
        boolean hasString = false;

        int index = 0;
        while (index < value.length()) {
            if (!(((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() == 10))) {

                hasString = true;
                break;

            }
            index++;
        }
        if (hasString) {
            value = Utils.getPhoneNo();
        }
        return value;
    }

    public static String getPhoneNo() {
        String value = "";
        try {

            System.out.println("Enter Valid Phone Number");
            value = bufferedReader.readLine();
            return Utils.checkNGetValidPhoneNumber(value);
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e);
        }
        return value;
    }

    public static double getDouble() {
        String stringValue;
        stringValue = Utils.getString();
        stringValue = Utils.checkNGetNotNull(stringValue);
        return Utils.checkHasStringNGetValidInt(stringValue);
    }

    public static int getInt() {
        String stringValue;
        stringValue = Utils.getString();
        stringValue = Utils.checkNGetNotNull(stringValue);
        return Utils.checkHasStringNGetValidInt(stringValue);
    }

    public static int checkHasStringNGetValidInt(String value) {
        int index = 0;
        boolean hasString = false;
        while (index < value.length()) {
            if (!((((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() < 9)))) {

                hasString = true;
                break;

            }

            index++;
        }
        if (hasString) {
            System.out.println("Enter Numbers Only!!");
            value = Utils.getString();
            return Utils.checkHasStringNGetValidInt(value);
        }
        return Integer.parseInt(value);
    }

    public static double checkHasStringNGetValidDouble(String value) {
        int index = 0;
        boolean hasString = false;
        while (index < value.length()) {
            if (!((((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() < 9)))) {

                hasString = true;
                break;

            }

            index++;
        }
        if (hasString) {
            System.out.println("Enter Numbers Only!!");
            value = Utils.getString();
            return Utils.checkHasStringNGetValidInt(value);
        }
        return Double.parseDouble(value);
    }

    public static String getString() {
        String str = "";
        try {
            str = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }
        return str;

    }

    public static Date parseDate(String value) {
        Date date = new Date();
        try {
            date = dateFormat.parse(value);
        } catch (Exception e) {
            System.out.println("Error Occured: " + e);
        }
        return date;
    }

    public static String printDate(Date date) {
        return dateFormat.format(date);
    }

    public static String getGender() {

        final int EXIT = 0;

        final int MALE = 1;
        final int FEMALE = 2;
        System.out.println("\t\t1.Male\n\t\t2.Female");
        int loopExit = 1;
        while (loopExit != EXIT) {
            int userChoice = Utils.getInt();
            switch (userChoice) {
                case MALE:
                    return "MALE";

                case FEMALE:
                    return "FEMALE";

                default:
                    System.out.println("Enter Valid Option!!");
            }
        }
        return "";
    }

    public static String checkNGetValidYear(String value) {
        int index = 0;
        boolean hasString = false;
        while (index < value.length()) {
            if (!((((value.charAt(index) >= '0') && (value.charAt(index) <= '9')) && (value.length() == 4)))) {
                hasString = true;
                break;
            }
            index++;
        }
        if (hasString) {
            value = Utils.getYear();
        }
        return value;
    }

    public static String getYear() {
        String value;
        System.out.println("Enter Valid Year(yyyy)!!");
        value = Utils.getString();
        return Utils.checkNGetValidYear(value);
    }

    public static String checkNGetValidMailID(String value) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(value);
        if (matcher.matches()) {
            return value;
        }
        return getMailID(value);
    }

    public static String getMailID(String value) {
        System.out.println("Enter Valid MailID:");
        value = Utils.getString();
        return Utils.checkNGetValidMailID(value);
    }

}
