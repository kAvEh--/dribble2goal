package ir.eynakgroup.dribble2goal.Util;

/**
 * Created by Eynak_PC2 on 2/22/2017.
 */

public class PersianDecoder {

    public String TransformText(String text) {
        text = RTLFlipper.flip(text);
        text += "  ";
        String ret2 = "";
        boolean isStrech = false;
        for (int i = 0; i < text.length() - 2; i++) {
            switch ((int) text.charAt(i)) {
                case 1570:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15701);
                    } else {
                        ret2 += ((char) 15700);
                    }
                    isStrech = true;
                    break;
                case 1571:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15711);
                    } else {
                        ret2 += ((char) 15710);
                    }
                    isStrech = true;
                    break;
                case 1574:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15743);
                        } else {
                            ret2 += ((char) 15741);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15740);
                        } else {
                            ret2 += ((char) 15742);
                        }
                    }
                    isStrech = true;
                    break;
                case 1575:
                    if (((int) text.charAt(i + 1)) == 1604) {
                        if (isInList(text.charAt(i + 2))) {
                            ret2 += ((char) 16045);
                        } else {
                            ret2 += ((char) 16044);
                        }
                        i++;
                        isStrech = true;
                    } else if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15751);
                        isStrech = true;
                    } else {
                        ret2 += ((char) 15750);
                        isStrech = true;
                    }
                    break;
                case 1576:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15763);
                        } else {
                            ret2 += ((char) 15761);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15760);
                        } else {
                            ret2 += ((char) 15762);
                        }
                    }
                    isStrech = true;
                    break;
                case 1578:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15783);
                        } else {
                            ret2 += ((char) 15781);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15780);
                        } else {
                            ret2 += ((char) 15782);
                        }
                    }
                    isStrech = true;
                    break;
                case 1579:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15793);
                        } else {
                            ret2 += ((char) 15791);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15790);
                        } else {
                            ret2 += ((char) 15792);
                        }
                    }
                    isStrech = true;
                    break;
                case 1580:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15803);
                        } else {
                            ret2 += ((char) 15801);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15800);
                        } else {
                            ret2 += ((char) 15802);
                        }
                    }
                    isStrech = true;
                    break;
                case 1581:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15813);
                        } else {
                            ret2 += ((char) 15811);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15810);
                        } else {
                            ret2 += ((char) 15812);
                        }
                    }
                    isStrech = true;
                    break;
                case 1582:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15823);
                        } else {
                            ret2 += ((char) 15821);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15820);
                        } else {
                            ret2 += ((char) 15822);
                        }
                    }
                    isStrech = true;
                    break;
                case 1583:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15831);
                    } else {
                        ret2 += ((char) 15830);
                    }
                    isStrech = true;
                    break;
                case 1584:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15841);
                    } else {
                        ret2 += ((char) 15840);
                    }
                    isStrech = true;
                    break;
                case 1585:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15851);
                    } else {
                        ret2 += ((char) 15850);
                    }
                    isStrech = true;
                    break;
                case 1586:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 15861);
                    } else {
                        ret2 += ((char) 15860);
                    }
                    isStrech = true;
                    break;
                case 1587:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15873);
                        } else {
                            ret2 += ((char) 15871);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15870);
                        } else {
                            ret2 += ((char) 15872);
                        }
                    }
                    isStrech = true;
                    break;
                case 1588:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15883);
                        } else {
                            ret2 += ((char) 15881);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15880);
                        } else {
                            ret2 += ((char) 15882);
                        }
                    }
                    isStrech = true;
                    break;
                case 1589:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15893);
                        } else {
                            ret2 += ((char) 15891);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15890);
                        } else {
                            ret2 += ((char) 15892);
                        }
                    }
                    isStrech = true;
                    break;
                case 1590:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15903);
                        } else {
                            ret2 += ((char) 15901);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15900);
                        } else {
                            ret2 += ((char) 15902);
                        }
                    }
                    isStrech = true;
                    break;
                case 1591:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15913);
                        } else {
                            ret2 += ((char) 15911);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15910);
                        } else {
                            ret2 += ((char) 15912);
                        }
                    }
                    isStrech = true;
                    break;
                case 1592:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15923);
                        } else {
                            ret2 += ((char) 15921);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15920);
                        } else {
                            ret2 += ((char) 15922);
                        }
                    }
                    isStrech = true;
                    break;
                case 1593:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15933);
                        } else {
                            ret2 += ((char) 15931);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15930);
                        } else {
                            ret2 += ((char) 15932);
                        }
                    }
                    isStrech = true;
                    break;
                case 1594:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15943);
                        } else {
                            ret2 += ((char) 15941);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 15940);
                        } else {
                            ret2 += ((char) 15942);
                        }
                    }
                    isStrech = true;
                    break;
                case 1601:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16013);
                        } else {
                            ret2 += ((char) 16011);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16010);
                        } else {
                            ret2 += ((char) 16012);
                        }
                    }
                    isStrech = true;
                    break;
                case 1602:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16023);
                        } else {
                            ret2 += ((char) 16021);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16020);
                        } else {
                            ret2 += ((char) 16022);
                        }
                    }
                    isStrech = true;
                    break;
                case 1604:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16043);
                        } else {
                            ret2 += ((char) 16041);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16040);
                        } else {
                            ret2 += ((char) 16042);
                        }
                    }
                    isStrech = true;
                    break;
                case 1605:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16053);
                        } else {
                            ret2 += ((char) 16051);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16050);
                        } else {
                            ret2 += ((char) 16052);
                        }
                    }
                    isStrech = true;
                    break;
                case 1606:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16063);
                        } else {
                            ret2 += ((char) 16061);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16060);
                        } else {
                            ret2 += ((char) 16062);
                        }
                    }
                    isStrech = true;
                    break;
                case 1607:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16073);
                        } else {
                            ret2 += ((char) 16071);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16070);
                        } else {
                            ret2 += ((char) 16072);
                        }
                    }
                    isStrech = true;
                    break;
                case 1608:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 16081);
                    } else {
                        ret2 += ((char) 16080);
                    }

                    isStrech = true;
                    break;
                case 1662:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16623);
                        } else {
                            ret2 += ((char) 16621);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16620);
                        } else {
                            ret2 += ((char) 16622);
                        }
                    }
                    isStrech = true;
                    break;
                case 1670:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16703);
                        } else {
                            ret2 += ((char) 16701);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 16700);
                        } else {
                            ret2 += ((char) 16702);
                        }
                    }
                    isStrech = true;
                    break;
                case 1688:
                    if (isInList(text.charAt(i + 1))) {
                        ret2 += ((char) 16881);
                    } else {
                        ret2 += ((char) 16880);
                    }

                    isStrech = true;
                    break;
                case 1705:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17053);
                        } else {
                            ret2 += ((char) 17051);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17050);
                        } else {
                            ret2 += ((char) 17052);
                        }
                    }
                    isStrech = true;
                    break;
                case 1711:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17113);
                        } else {
                            ret2 += ((char) 17111);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17110);
                        } else {
                            ret2 += ((char) 17112);
                        }
                    }
                    isStrech = true;
                    break;
                case 1740:
                    if (isStrech) {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17403);
                        } else {
                            ret2 += ((char) 17401);
                        }
                    } else {
                        if (isInList(text.charAt(i + 1))) {
                            ret2 += ((char) 17400);
                        } else {
                            ret2 += ((char) 17402);
                        }
                    }
                    isStrech = true;
                    break;
                default:
                    ret2 += text.charAt(i);
                    isStrech = false;
                    break;

            }
        }
        return ret2;
    }

    private boolean isInList(char c) {
        switch ((int) c) {
            case 1574:
                return true;
            case 1576:
                return true;
            case 1578:
                return true;
            case 1579:
                return true;
            case 1580:
                return true;
            case 1581:
                return true;
            case 1582:
                return true;
            case 1587:
                return true;
            case 1588:
                return true;
            case 1589:
                return true;
            case 1590:
                return true;
            case 1591:
                return true;
            case 1592:
                return true;
            case 1593:
                return true;
            case 1594:
                return true;
            case 1601:
                return true;
            case 1602:
                return true;
            case 1604:
                return true;
            case 1605:
                return true;
            case 1606:
                return true;
            case 1607:
                return true;
            case 1662:
                return true;
            case 1670:
                return true;
            case 1705:
                return true;
            case 1711:
                return true;
            case 1740:
                return true;
        }
        return false;
    }
}