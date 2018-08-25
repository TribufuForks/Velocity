package com.velocitypowered.proxy.protocol.packet;

import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolConstants;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import io.netty.buffer.ByteBuf;

import static com.velocitypowered.proxy.protocol.ProtocolConstants.MINECRAFT_1_13;
import static com.velocitypowered.proxy.protocol.ProtocolConstants.MINECRAFT_1_9;

public class TabCompleteRequest implements MinecraftPacket {
    private int transactionId;
    private String command;
    private boolean assumeCommand;
    private boolean hasPosition;
    private long position;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isAssumeCommand() {
        return assumeCommand;
    }

    public void setAssumeCommand(boolean assumeCommand) {
        this.assumeCommand = assumeCommand;
    }

    public boolean isHasPosition() {
        return hasPosition;
    }

    public void setHasPosition(boolean hasPosition) {
        this.hasPosition = hasPosition;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TabCompleteRequest{" +
                "transactionId=" + transactionId +
                ", command='" + command + '\'' +
                ", assumeCommand=" + assumeCommand +
                ", hasPosition=" + hasPosition +
                ", position=" + position +
                '}';
    }

    @Override
    public void decode(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        if (protocolVersion >= MINECRAFT_1_13) {
            this.transactionId = ProtocolUtils.readVarInt(buf);
            this.command = ProtocolUtils.readString(buf);
        } else {
            this.command = ProtocolUtils.readString(buf);
            if (protocolVersion >= MINECRAFT_1_9) {
                this.assumeCommand = buf.readBoolean();
            }
            this.hasPosition = buf.readBoolean();
            if (hasPosition) {
                this.position = buf.readLong();
            }
        }
    }

    @Override
    public void encode(ByteBuf buf, ProtocolConstants.Direction direction, int protocolVersion) {
        if (protocolVersion >= MINECRAFT_1_13) {
            ProtocolUtils.writeVarInt(buf, transactionId);
            ProtocolUtils.writeString(buf, command);
        } else {
            ProtocolUtils.writeString(buf, command);
            if (protocolVersion >= MINECRAFT_1_9) {
                buf.writeBoolean(assumeCommand);
            }
            buf.writeBoolean(hasPosition);
            if (hasPosition) {
                buf.writeLong(position);
            }
        }
    }
}