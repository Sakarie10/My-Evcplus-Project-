import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Evcplus {
    private static final double INITIAL_BALANCE = 300;
    private static final String CORRECT_PIN = "1234";
    private static final String[] MAIN_MENU_OPTIONS = {
            "Itus Haraaga",
            "Kaarka hadalka",
            "Bixi Biil",
            "U wareeji EVCPlus",
            "Warbixin Kooban",
            "Salaam Bank",
            "Maareynta",
            "TAAJ",
            "Bill Payment"
    };

    private static final String[] AIRTIME_MENU_OPTIONS = {
            "Ku Shubo Airtime",
            "Ugu Shub Airtime",
            "MIFI Packages",
            "Ku shubo Internet",
            "Ugu shub qof kale MMT"
    };

    private static final String[] MIFI_PACKAGE_TYPES = {
            "isbuucle (Weekly)",
            "Maalinle (daily)",
            "Bille (Monthly)"
    };

    private static final String[] WEEKLY_PACKAGES = {
            "$5 = 10 GB",
            "$10 = 25 GB"
    };

    private static final String[] DAILY_PACKAGES = {
            "$1 = 2 GB",
            "$2 = 5 GB"
    };

    private static final String[] MONTHLY_PACKAGES = {
            "$20 = 40GB",
            "$25 = monthly Unlimit"
    };

    private static final String[] INTERNET_BUNDLE_OPTIONS = {
            "Isbuucle(Weekly)",
            "TIME BASED ON PACKAGES",
            "DATA",
            "Maalinle(daily)",
            "Bille MIFI"
    };

    private static Scanner scanner = new Scanner(System.in);
    private static double balance = INITIAL_BALANCE;
    private static ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Africa/Mogadishu"));
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

    public static void main(String[] args) {
        displayWelcomeScreen();
        scanner.close();
    }

    private static void displayWelcomeScreen() {
        System.out.print("Fadlan geli koodhka USSD (*770#): ");
        String code = scanner.nextLine();

        if (!code.equals("*770#")) {
            System.out.println("Koodhka aad gelisay waa khalad.");
            return;
        }

        if (!verifyPin()) {
            System.out.println("\nMacmiil 3 jeer ka badan lama ogal. Fadlan sug 30 daqiiqo.");
            return;
        }

        displayMainMenu();
    }

    private static boolean verifyPin() {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Fadlan geli PIN-kaaga (4 lambar): ");
            String pin = scanner.nextLine();

            if (pin.equals(CORRECT_PIN)) {
                return true;
            } else {
                System.out.println("PIN-ka waa khalad. Fadlan isku day mar kale.");
                attempts++;
            }
        }
        return false;
    }

    private static void displayMainMenu() {
        System.out.println("\nKu soo dhawoow adeegga EVCPlus\n");
        for (int i = 0; i < MAIN_MENU_OPTIONS.length; i++) {
            System.out.println((i+1) + ". " + MAIN_MENU_OPTIONS[i]);
        }

        System.out.print("\nFadlan dooro adeega (1-9): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // buffer cleanup

        switch (choice) {
            case 1:
                checkBalance();
                break;
            case 2:
                airtimeMenu();
                break;
            case 3:
                billPaymentMenu();
                break;
            case 4:
                transferMoney();
                break;
            default:
                System.out.println("Adeegga aad dooratay wali lama hawlgelin.");
        }
    }

    private static void checkBalance() {
        System.out.println("<-EVCPlus-> Haraagaagu waa $" + balance);
    }

    private static void airtimeMenu() {
        System.out.println("Kaarka hadalka");
        for (int i = 0; i < AIRTIME_MENU_OPTIONS.length; i++) {
            System.out.println((i+1) + ". " + AIRTIME_MENU_OPTIONS[i]);
        }

        System.out.print("\nFadlan dooro: ");
        int subChoice = scanner.nextInt();
        scanner.nextLine();

        switch (subChoice) {
            case 1:
                topUpOwnAirtime();
                break;
            case 2:
                transferAirtime();
                break;
            case 3:
                mifiPackagesMenu();
                break;
            case 4:
                internetBundleMenu();
                break;
            case 5:
                transferToOther();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void topUpOwnAirtime() {
        System.out.print("Fadlan Geli Lacagta: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount > 0.25) {
            System.out.println("Ma hubtaa inaad $" + amount + " ugu shubto Undefined?");
            System.out.println("1. Haa");
            System.out.println("2. Maya");
            System.out.print("Dooro: ");
            int confirm = scanner.nextInt();

            if (confirm == 1) {
                balance -= amount;
                System.out.println("[-EVCPLus-] $" + amount + "\nayaad Ku shubatay,\n" +
                        "Tar: " + currentTime.format(dateFormat) + ", \n" +
                        "Haraagaagu waa $" + balance + ". \n" +
                        "La soo deg App-ka WAAFI");
            } else {
                System.out.println("Mahadsanid!");
            }
        } else {
            System.out.println("Fadlan geli lacag ka badan $0.25");
        }
    }

    private static void transferAirtime() {
        System.out.print("Fadlan Geli Mobile-ka: ");
        String phoneNumber = scanner.nextLine();

        if (isValidPhoneNumber(phoneNumber)) {
            System.out.print("Fadlan Geli Lacagta: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0.25) {
                System.out.println("Ma hubtaa inaad $" + amount + " ugu shubtid \n" + phoneNumber + "?");
                System.out.println("1. Haa");
                System.out.println("2. Maya");
                System.out.print("Dooro: ");
                int confirm = scanner.nextInt();

                if (confirm == 1) {
                    balance -= amount;
                    System.out.println("[-EVCPLus-] $" + amount + " ayaad Ugu shubtay " + phoneNumber + ",\n" +
                            "Tar: " + currentTime.format(dateFormat) + ", \n" +
                            "Haraagaagu waa $" + balance + ". \n" +
                            "La soo deg App-ka WAAFI");
                } else {
                    System.out.println("Mahadsanid!");
                }
            } else {
                System.out.println("Fadlan geli lacag ka badan $0.25");
            }
        } else {
            System.out.println("Lambarka waa khaldan. Waa inuu ka bilaabmaa 61 ama 68, kana koobnaadaa 9 lambar.");
        }
    }

    private static void mifiPackagesMenu() {
        System.out.println("--Internet Bundle Recharge--");
        for (int i = 0; i < MIFI_PACKAGE_TYPES.length; i++) {
            System.out.println((i+1) + ". " + MIFI_PACKAGE_TYPES[i]);
        }

        System.out.print("Fadlan dooro: ");
        int packageType = scanner.nextInt();

        switch (packageType) {
            case 1: // Weekly
                displayPackageOptions(WEEKLY_PACKAGES, "Usbuucle");
                break;
            case 2: // Daily
                displayPackageOptions(DAILY_PACKAGES, "Maalinle");
                break;
            case 3: // Monthly
                displayPackageOptions(MONTHLY_PACKAGES, "Bille");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void displayPackageOptions(String[] packages, String packageType) {
        System.out.println("Fadlan dooro bundle ka");
        for (int i = 0; i < packages.length; i++) {
            System.out.println((i+1) + ". " + packages[i]);
        }

        int choice = scanner.nextInt();
        if (choice < 1 || choice > packages.length) {
            System.out.println("Invalid choice");
            return;
        }

        double amount = getPackageAmount(packages[choice-1]);
        System.out.println("Fadlan Geli MIFI Number kaaga: ");
        int mifiNumber = scanner.nextInt();

        if (mifiNumber == 622077) {
            System.out.println("Ma Hubtaa in aad $" + amount + " MIFI shubanayso Undefined?");
            System.out.println("1. haa \n2. Maya");
            int confirm = scanner.nextInt();

            if (confirm == 1) {
                balance -= amount;
                System.out.println("[-EVCPLus-] $" + amount + " ayaad Ku shubatay " +
                        "Waxaa heshay\n" + packages[choice-1].split("=")[1].trim() + " " + packageType +
                        ",\nTar: " + currentTime.format(dateFormat) + ". \n" +
                        "La soo deg App-ka WAAFI");
            } else {
                System.out.println("Mahadsanid");
            }
        }
    }

    private static double getPackageAmount(String packageStr) {
        return Double.parseDouble(packageStr.split("=")[0].replace("$", "").trim());
    }

    private static void internetBundleMenu() {
        for (int i = 0; i < INTERNET_BUNDLE_OPTIONS.length; i++) {
            System.out.println((i+1) + ". " + INTERNET_BUNDLE_OPTIONS[i]);
        }

        System.out.print("Fadlan dooro: ");
        int choice = scanner.nextInt();

        if (choice < 1 || choice > INTERNET_BUNDLE_OPTIONS.length) {
            System.out.println("Invalid choice");
            return;
        }

        System.out.print("Fadlan Geli Lacagta: ");
        double amount = scanner.nextDouble();

        if (amount >= 0.25) {
            System.out.println("Ma hubtaa inaad " + amount + "ku shubatid? \n1. Haa \n2. Maya");
            int confirm = scanner.nextInt();

            if (confirm == 1) {
                balance -= amount;
                System.out.println("[-EVCPLus-] $" + amount + " ayaad Ku shubatay " +
                        "Waxaa heshay\n" + INTERNET_BUNDLE_OPTIONS[choice-1] +
                        ",\nTar: " + currentTime.format(dateFormat) + ". \n" +
                        "\nHaraagaagu waa $" + balance +
                        "La soo deg App-ka WAAFI");
            } else {
                System.out.println("Mahadsanid");
            }
        }
    }

    private static void transferToOther() {
        System.out.print("Fadlan Geli Mobile-ka: ");
        String phoneNumber = scanner.nextLine();

        if (isValidPhoneNumber(phoneNumber)) {
            System.out.print("Fadlan Geli Lacagta: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0.25) {
                System.out.println("Ma hubtaa inaad $" + amount + " ugu shubtid MiFi " + phoneNumber + "?");
                System.out.println("1. Haa");
                System.out.println("2. Maya");
                System.out.print("Dooro: ");
                int confirm = scanner.nextInt();

                if (confirm == 1) {
                    balance -= amount;
                    System.out.println("[-EVCPLus-] $" + amount + " ayaad Ugu shubtay MiFi" + phoneNumber + ",\n" +
                            "Tar: " + currentTime.format(dateFormat) + ", \n" +
                            "Haraagaagu waa $" + balance + ". \n" +
                            "La soo deg App-ka WAAFI");
                } else {
                    System.out.println("Mahadsanid!");
                }
            }
        }
    }

    private static void billPaymentMenu() {
        System.out.println("Bixi Biil");
        System.out.println("1. Post Paid");
        System.out.println("2. Ku Iibso");
        // Implementation would go here
    }

    private static void transferMoney() {
        System.out.print("Fadlan Geli Mobile-ka: ");
        String phoneNumber = scanner.nextLine();

        if (isValidPhoneNumber(phoneNumber)) {
            System.out.print("Fadlan Geli Lacagta: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0) {
                System.out.println("Ma hubtaa inaad $" + amount + " u wareejinayso \n" + phoneNumber + "?");
                System.out.println("1. Haa");
                System.out.println("2. Maya");
                System.out.print("Dooro: ");
                int confirm = scanner.nextInt();

                if (confirm == 1) {
                    balance -= amount;
                    System.out.println("[-EVCPLus-] $" + amount + " ayaad uwareejisay " + phoneNumber + ",\n" +
                            "Tar: " + currentTime.format(dateFormat) + ", \n" +
                            "Haraagaagu waa $" + balance + ". \n" +
                            "La soo deg App-ka WAAFI");
                } else {
                    System.out.println("Mahadsanid!");
                }
            }
        } else {
            System.out.println("Lambarka waa khaldan. Waa inuu ka bilaabmaa 61 ama 68, kana koobnaadaa 9 lambar.");
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return (phoneNumber.startsWith("61") || phoneNumber.startsWith("68")) && phoneNumber.length() == 9;
    }
}