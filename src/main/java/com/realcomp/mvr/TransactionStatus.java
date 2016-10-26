package com.realcomp.mvr;

/**
 * A code describing the status of a title application after it has been received by the county tax office.
 *
 */
public enum TransactionStatus{

    APPROVED,
    TRANSMITTED,
    EXAMINATION,
    LEGAL_RESTRAINT,
    PRINTING,
    REJECTED,
    SUPERSEDED,
    STOLEN,
    DELETED,
    OTHER,
    UNKNOWN
}
