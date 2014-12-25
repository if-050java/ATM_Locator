package com.ss.atmlocator.entity;

/**
 * Enum for representing Atm State
 * NORMAL - atm with coordinates
 * BAD_ADDRESS - can't recognize coordinates
 * NO_LOCATION - new atm
 */
public enum AtmState {NORMAL, DISABLED, BAD_ADDRESS, NO_LOCATION};
