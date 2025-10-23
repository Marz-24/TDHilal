class NodeQueue {
    String nama;
    String[][] labirin;
    String command;
    int xStart, yStart, xFinish, yFinish;
    int step;
    NodeQueue next, prev;

    public NodeQueue() {
        
    }

    public NodeQueue(String nama, String[][] labirin, String command, int step, int xStart, int yStart, int xFinish, int yFinish) {
        this.nama = nama;
        this.labirin = labirin;
        this.command = command;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xFinish = xFinish;
        this.yFinish = yFinish;
        this.step = step;
        this.next = null;
        this.prev = null;
    }
}

public class QueueTD {
    NodeQueue head, tail;
    int size;

    public QueueTD() {
        head = tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(String nama, String[][] labirin, String command, int step, int xStart, int yStart, int xFinish, int yFinish) {
        String[][] labirinCopy = new String[labirin.length][labirin[0].length];
        for (int i = 0; i < labirinCopy.length; i++) {
            for (int j = 0; j < labirinCopy[i].length; j++) {
                labirinCopy[i][j] = labirin[i][j];
            }
        }
        NodeQueue baru = new NodeQueue(nama, labirinCopy, command, step, xStart, yStart, xFinish, yFinish);

        if (isEmpty()) {
            head = tail = baru;
        } else {
            baru.next = head;
            head.prev = baru;
            head = baru;
        }
        size++;
    }

    public void addLast(String nama, String[][] labirin, String command, int step, int xStart, int yStart, int xFinish, int yFinish) {
        String[][] labirinCopy = new String[labirin.length][labirin[0].length];
        for (int i = 0; i < labirinCopy.length; i++) {
            for (int j = 0; j < labirinCopy[i].length; j++) {
                labirinCopy[i][j] = labirin[i][j];
            }
        }
        NodeQueue baru = new NodeQueue(nama, labirinCopy, command, step, xStart, yStart, xFinish, yFinish);

        if (isEmpty()) {
            head = tail = baru;
        } else {
            baru.prev = tail;
            tail.next = baru;
            tail = baru;
        }
        size++;
    }

    public void insertBefore(String nama, String[][] labirin, String command, int step, String dicari, int xStart, int yStart, int xFinish, int yFinish) {
        NodeQueue baru = new NodeQueue(nama, labirin, command, step, xStart, yStart, xFinish, yFinish);
        NodeQueue temp = head;

        while (temp != null) {
            if (temp.nama.equals(dicari)) {
                if (temp == head) {
                    addFirst(nama, labirin, command, step, xStart, yStart, xFinish, yFinish);
                    break;
                } else {
                    baru.next = temp;
                    baru.prev = temp.prev;
                    temp.prev.next = baru;
                    temp.prev = baru;
                    size++;
                    break;
                }
            }
            temp = temp.next;
        }
    }

    public void insertAfter(String nama, String[][] labirin, String command, int step, String dicari, int xStart, int yStart, int xFinish, int yFinish) {
        NodeQueue baru = new NodeQueue(nama, labirin, command, step, xStart, yStart, xFinish, yFinish);
        NodeQueue temp = head;

        while (temp != null) {
            if (temp.nama.equals(dicari)) {
                if (temp == tail) {
                    addLast(nama, labirin, command, step, xStart, yStart, xFinish, yFinish);
                    break;
                } else {
                    baru.prev = temp;
                    baru.next = temp.next;
                    temp.next.prev = baru;
                    temp.next = baru;
                    size++;
                    break;
                }
            }
            temp = temp.next;
        }
    }

    public void jelajah() {
        NodeQueue tempQueue = head;
        
        while (tempQueue != null) {
            ListTD commandList = new ListTD();
            String[] commandSplit = tempQueue.command.split("\\s");
            for (int i = 1; i < 5; i++) {
                commandList.addLast(commandSplit[i]);
            }

            NodeList tempList = commandList.head;
            StackTD validIndex = new StackTD();
            validIndex.push(tempQueue.xStart, tempQueue.yStart);
            int jumCommand = 1;
            while (tempList != null) {
                switch (tempList.data) {
                    case "UP":
                        if (validIndex.y() >= tempQueue.labirin.length || tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[XX]")) {
                            if (tempList.next == null) {
                                tempList = commandList.head;
                            } else {
                                tempList = tempList.next;
                            }
                            validIndex.pop();
                            if (jumCommand >= 4) {
                                validIndex.pop();
                            }
                            jumCommand++;
                        } else if (tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[OO]")) {
                            if (validIndex.x() == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                tempList = null; 
                            } else {
                                tempQueue.labirin[validIndex.x()][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                validIndex.push(validIndex.x(), validIndex.y()+1);
                                if ((validIndex.y()+1 >= tempQueue.labirin.length)) {
                                    jumCommand = 1;
                                }
                                tempQueue.step++;
                            }
                        } else {
                            if (validIndex.y()+1 >= tempQueue.labirin.length) {
                                validIndex.push(validIndex.x(), validIndex.y()+1);
                            } else if (!tempQueue.labirin[validIndex.x()][validIndex.y()+1].equals("[OO]")) {
                                if (tempQueue.labirin[validIndex.x()][validIndex.y()+1].equals("[XX]") && jumCommand < 4) {
                                    validIndex.push(validIndex.x(), validIndex.y()+1);
                                } else if (tempList.next == null) {
                                    tempList = commandList.head;
                                    if (jumCommand >= 4) {
                                        jumCommand = 1;
                                        validIndex.pop();
                                    }
                                } else {
                                    tempList = tempList.next;
                                    jumCommand++;
                                }
                            } else if (tempQueue.labirin[validIndex.x()][validIndex.y()+1].equals("[OO]")) {
                                if (validIndex.x() == tempQueue.xFinish && validIndex.y()+1 == tempQueue.yFinish) {
                                    tempQueue.labirin[validIndex.x()][validIndex.y()+1] = String.format("[%02d]", tempQueue.step);
                                    tempList = null; 
                                } else {
                                    tempQueue.labirin[validIndex.x()][validIndex.y()+1] = String.format("[%02d]", tempQueue.step);
                                    validIndex.push(validIndex.x(), validIndex.y()+1);
                                    jumCommand = 1;
                                    tempQueue.step++;
                                }
                            }
                        }
                        break;
                
                    case "DOWN":
                        if (validIndex.y() <= -1 || tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[XX]")) {
                            if (tempList.next == null) {
                                tempList = commandList.head;
                            } else {
                                tempList = tempList.next;
                            }
                            validIndex.pop();
                            if (jumCommand >= 4) {
                                validIndex.pop();
                            }
                            jumCommand++;
                        } else if (tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[OO]")) {
                            if (validIndex.x() == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                tempList = null; 
                            } else {
                                tempQueue.labirin[validIndex.x()][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                validIndex.push(validIndex.x(), validIndex.y()-1);
                                if (!(validIndex.y()-1 <= -1)) {
                                    jumCommand = 1;
                                }
                                tempQueue.step++;
                            }
                        } else {
                            if (validIndex.y()-1 <= -1) {
                                validIndex.push(validIndex.x(), validIndex.y()-1);
                            } else if (!tempQueue.labirin[validIndex.x()][validIndex.y()-1].equals("[OO]")) {
                                if (tempQueue.labirin[validIndex.x()][validIndex.y()-1].equals("[XX]") && jumCommand < 4) {
                                    validIndex.push(validIndex.x(), validIndex.y()-1);
                                } else if (tempList.next == null) {
                                    tempList = commandList.head;
                                    if (jumCommand >= 4) {
                                        jumCommand = 1;
                                        validIndex.pop();
                                    }
                                } else {
                                    tempList = tempList.next;
                                    jumCommand++;
                                }
                            } else if (tempQueue.labirin[validIndex.x()][validIndex.y()-1].equals("[OO]")) {
                                if (validIndex.x() == tempQueue.xFinish && validIndex.y()-1 == tempQueue.yFinish) {
                                    tempQueue.labirin[validIndex.x()][validIndex.y()-1] = String.format("[%02d]", tempQueue.step);
                                    tempList = null; 
                                } else {
                                    tempQueue.labirin[validIndex.x()][validIndex.y()-1] = String.format("[%02d]", tempQueue.step);
                                    validIndex.push(validIndex.x(), validIndex.y()-1);
                                    jumCommand = 1;
                                    tempQueue.step++;
                                }
                            }
                        }
                        break;
                        
                    case "RIGHT":
                        if (validIndex.x() >= tempQueue.labirin.length || tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[XX]")) {
                            if (tempList.next == null) {
                                tempList = commandList.head;
                            } else {
                                tempList = tempList.next;
                            }
                            validIndex.pop();
                            if (jumCommand >= 4) {
                                validIndex.pop();
                            }
                            jumCommand++;
                        } else if (tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[OO]")) {
                            if (validIndex.x() == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                tempList = null; 
                            } else {
                                tempQueue.labirin[validIndex.x()][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                validIndex.push(validIndex.x()+1, validIndex.y());
                                if (!(validIndex.x()+1 >= tempQueue.labirin.length)) {
                                    jumCommand = 1;
                                }
                                tempQueue.step++;
                            }
                        } else {
                            if (validIndex.x()+1 >= tempQueue.labirin.length) {
                                validIndex.push(validIndex.x()+1, validIndex.y());
                            } else if (!tempQueue.labirin[validIndex.x()+1][validIndex.y()].equals("[OO]")) {
                                if (tempQueue.labirin[validIndex.x()+1][validIndex.y()].equals("[XX]") && jumCommand < 4) {
                                    validIndex.push(validIndex.x()+1, validIndex.y());
                                } else if (tempList.next == null) {
                                    tempList = commandList.head;
                                    if (jumCommand >= 4) {
                                        jumCommand = 1;
                                        validIndex.pop();
                                    }
                                } else {
                                    tempList = tempList.next;
                                    jumCommand++;
                                }
                            } else if (tempQueue.labirin[validIndex.x()+1][validIndex.y()].equals("[OO]")) {
                                if (validIndex.x()+1 == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                    tempQueue.labirin[validIndex.x()+1][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                    tempList = null; 
                                } else {
                                    tempQueue.labirin[validIndex.x()+1][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                    validIndex.push(validIndex.x()+1, validIndex.y());
                                    jumCommand = 1;
                                    tempQueue.step++;
                                }
                            }
                        }
                        break;
                        
                    case "LEFT":
                        if (validIndex.x() <= -1 || tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[XX]")) {
                            if (tempList.next == null) {
                                tempList = commandList.head;
                            } else {
                                tempList = tempList.next;
                            }
                            validIndex.pop();
                            if (jumCommand >= 4) {
                                validIndex.pop();
                            }
                            jumCommand++;
                        } else if (tempQueue.labirin[validIndex.x()][validIndex.y()].equals("[OO]")) {
                            if (validIndex.x() == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                tempList = null; 
                            } else {
                                tempQueue.labirin[validIndex.x()][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                validIndex.push(validIndex.x()-1, validIndex.y());
                                if (!(validIndex.x()-1 <= -1)) {
                                    jumCommand = 1;
                                }
                                tempQueue.step++;
                            }
                        } else {
                            if (validIndex.x()-1 <= -1) {
                                validIndex.push(validIndex.x()-1, validIndex.y());
                            } else if (!tempQueue.labirin[validIndex.x()-1][validIndex.y()].equals("[OO]")) {
                                if (tempQueue.labirin[validIndex.x()-1][validIndex.y()].equals("[XX]") && jumCommand < 4) {
                                    validIndex.push(validIndex.x()-1, validIndex.y());
                                } else if (tempList.next == null) {
                                    tempList = commandList.head;
                                    if (jumCommand >= 4) {
                                        jumCommand = 1;
                                        validIndex.pop();
                                    }
                                } else {
                                    tempList = tempList.next;
                                    jumCommand++;
                                }
                            } else if (tempQueue.labirin[validIndex.x()-1][validIndex.y()].equals("[OO]")) {
                                if (validIndex.x()-1 == tempQueue.xFinish && validIndex.y() == tempQueue.yFinish) {
                                    tempQueue.labirin[validIndex.x()-1][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                    tempList = null; 
                                } else {
                                    tempQueue.labirin[validIndex.x()-1][validIndex.y()] = String.format("[%02d]", tempQueue.step);
                                    validIndex.push(validIndex.x()-1, validIndex.y());
                                    jumCommand = 1;
                                    tempQueue.step++;
                                }
                            }
                        }
                        break;
                
                    default:
                        break;
                }
            }
            tempQueue = tempQueue.next;
        }
    }

    public QueueTD sort() {
        QueueTD hasil = new QueueTD();
        NodeQueue temp = head;

        while (temp != null) {
            if (temp == head) {
                hasil.addFirst(temp.nama, temp.labirin, temp.command, temp.step, temp.xStart, temp.yStart, temp.xFinish, temp.yFinish);
            } else {
                NodeQueue temp2 = hasil.head;

                while (temp2 != null) {
                    if (temp.step < temp2.step) {
                        hasil.insertBefore(temp.nama, temp.labirin, temp.command, temp.step, temp2.nama, temp.xStart, temp.yStart, temp.xFinish, temp.yFinish);
                        break;
                    } else if (temp.step == temp2.step) {
                        hasil.insertAfter(temp.nama, temp.labirin, temp.command, temp.step, temp2.nama, temp.xStart, temp.yStart, temp.xFinish, temp.yFinish);
                        break;
                    } else if (temp.step > temp2.step) {
                        if (temp2 == hasil.tail) {
                            hasil.addLast(temp.nama, temp.labirin, temp.command, temp.step, temp.xStart, temp.yStart, temp.xFinish, temp.yFinish);
                            break;
                        } else {
                            temp2 = temp2.next;
                        }
                    }
                }
            }
            temp = temp.next;
        }

        return hasil;
    }

    public String[][] rotate(String[][] labirin) {
        String[][] labirinRotate = new String[labirin.length][labirin.length];
        for (int i = (labirin.length-1); i >= 0; i--) {
            for (int j = 0; j < labirin[i].length; j++) {
                labirinRotate[(labirin.length-1) - i][j] = labirin[j][i];
            }
        }
        return labirinRotate;
    }

    public void cetakLabirin() {
        NodeQueue temp = head;

        while (temp != null) {
            System.out.println(temp.nama);
            temp.labirin = rotate(temp.labirin);
            for (int i = 0; i < temp.labirin.length; i++) {
                for (int j = 0; j < temp.labirin[i].length; j++) {
                    System.out.print(temp.labirin[i][j]);
                }
                System.out.println();
            }
            temp = temp.next;
        }
        System.out.println();
    }

    public void cetakNama() {
        NodeQueue temp = head;

        while (temp != null) {
            if (temp == head) {
                System.out.print("FASTEST ");
            }
            System.out.println(temp.nama + " " + temp.step + " steps");
            temp = temp.next;
        }
    }
}